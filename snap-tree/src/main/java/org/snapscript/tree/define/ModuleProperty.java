package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.AccessorProperty;
import org.snapscript.core.function.ModuleAccessor;
import org.snapscript.core.module.Module;
import org.snapscript.core.property.Property;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.DeclarationAllocator;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.constraint.DeclarationConstraint;
import org.snapscript.tree.literal.TextLiteral;

public class ModuleProperty {
   
   private final DeclarationAllocator allocator;
   private final DeclarationConstraint constraint;
   private final NameReference reference;
   private final Evaluation value;
   
   public ModuleProperty(TextLiteral identifier) {
      this(identifier, null, null);
   }
   
   public ModuleProperty(TextLiteral identifier, Constraint constraint) {      
      this(identifier, constraint, null);
   }
   
   public ModuleProperty(TextLiteral identifier, Evaluation value) {
      this(identifier, null, value);
   }
   
   public ModuleProperty(TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.allocator = new ModulePropertyAllocator(constraint, value);
      this.constraint = new DeclarationConstraint(constraint);
      this.reference = new NameReference(identifier);
      this.value = value;
   }  
   
   public Property define(ModuleBody body, Scope scope, int modifiers) throws Exception {
      String name = reference.getName(scope);
      Accessor accessor = define(body, scope);
      Constraint require = constraint.getConstraint(scope, modifiers);
      
      return new AccessorProperty(name, null, require, accessor, modifiers);
   }

   public Value compile(ModuleBody body, Scope scope, int modifiers) throws Exception {
      String name = reference.getName(scope);
      Value value = allocator.compile(scope, name, modifiers);
      State state = scope.getState();
      
      try {
         state.add(name, value);
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      }
      return value;
   }
   
   public Value execute(ModuleBody body, Scope scope, int modifiers) throws Exception {
      String name = reference.getName(scope);
      Value value = allocator.allocate(scope, name, modifiers);
      State state = scope.getState();
     
      try {
         state.add(name, value);
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      } 
      return value;
   }
   
   private Accessor define(ModuleBody body, Scope scope) throws Exception {
      Module module = scope.getModule();
      String name = reference.getName(scope);

      if(value != null) {
         value.define(scope);
      }
      return new ModuleAccessor(module, body, scope, name);
   }
}