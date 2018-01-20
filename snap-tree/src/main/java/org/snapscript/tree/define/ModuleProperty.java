package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.AccessorProperty;
import org.snapscript.core.function.ModuleAccessor;
import org.snapscript.core.property.Property;
import org.snapscript.tree.DeclarationAllocator;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.constraint.ConstraintReference;
import org.snapscript.tree.literal.TextLiteral;

public class ModuleProperty {
   
   private final DeclarationAllocator allocator;
   private final ConstraintReference constraint;
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
      this.constraint = new ConstraintReference(constraint);
      this.reference = new NameReference(identifier);
      this.value = value;
   }  
   
   public Property compile(ModuleBody body, Scope scope, int modifiers) throws Exception {
      Type type = constraint.getConstraint(scope);
      String name = reference.getName(scope);
      Accessor accessor = compile(body, scope);
      
      return new AccessorProperty(name, null, type, accessor, modifiers);
   }
   
   public Value execute(ModuleBody body, Scope scope, int modifiers) throws Exception {
      String name = reference.getName(scope);
      Value value = allocator.allocate(scope, name, modifiers);
      State state = scope.getState();
      
      state.add(name, value);
      return value;
   }
   
   private Accessor compile(ModuleBody body, Scope scope) throws Exception {
      Module module = scope.getModule();
      String name = reference.getName(scope);

      if(value != null) {
         value.compile(scope);
      }
      return new ModuleAccessor(module, body, scope, name);
   }
}