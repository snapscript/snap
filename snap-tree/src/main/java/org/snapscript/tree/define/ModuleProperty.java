package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.tree.DeclarationAllocator;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.literal.TextLiteral;

public class ModuleProperty {
   
   private final DeclarationAllocator allocator;
   private final NameReference reference;
   
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
      this.reference = new NameReference(identifier);
   }   
   
   public Value create(Scope scope, int modifiers) throws Exception {
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
}