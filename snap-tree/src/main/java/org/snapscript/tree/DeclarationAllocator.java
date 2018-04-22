package org.snapscript.tree;

import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Local;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.tree.constraint.DeclarationConstraint;

public class DeclarationAllocator {

   private final DeclarationConverter converter;
   private final Constraint constraint;
   private final Evaluation expression;
   
   public DeclarationAllocator(Constraint constraint, Evaluation expression) {      
      this.constraint = new DeclarationConstraint(constraint);
      this.converter = new DeclarationConverter();
      this.expression = expression;
   }   
   
   public <T extends Value> T compile(Scope scope, String name, int modifiers) throws Exception {
      Type type = constraint.getType(scope);
      
      if(expression != null) {
         Constraint object = expression.compile(scope, null);
         Type real = object.getType(scope);
         
         if(real != null) {
            object = converter.compile(scope, real, constraint, name);        
         }
         return assign(scope, name, object, object, modifiers);
      }
      return declare(scope, name, constraint, modifiers); // nothing assigned yet
   }
   
   
   public <T extends Value> T allocate(Scope scope, String name, int modifiers) throws Exception {
      Type type = constraint.getType(scope);
      Object object = null;
      
      if(expression != null) {
         Value value = expression.evaluate(scope, null);
         Object original = value.getValue();
         
         if(type != null) {
            object = converter.convert(scope, original, constraint, name);
         } else {
            object = original;
         }
         return assign(scope, name, object, constraint, modifiers);
      }
      return declare(scope, name, constraint, modifiers);
   }   
   
   protected <T extends Value> T declare(Scope scope, String name, Constraint type, int modifiers) throws Exception {
      if(ModifierType.isConstant(modifiers)) {
         return (T)Local.getConstant(null, name, type);
      }
      return (T)Local.getReference(null, name, type);
   }
   
   protected <T extends Value> T assign(Scope scope, String name, Object value, Constraint type, int modifiers) throws Exception {
      if(ModifierType.isConstant(modifiers)) {
         return (T)Local.getConstant(value, name, type);
      }
      return (T)Local.getReference(value, name, type);
   }
}