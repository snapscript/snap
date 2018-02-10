package org.snapscript.tree;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Local;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.constraint.ConstraintReference;

public class DeclarationAllocator {

   private final DeclarationConverter converter;
   private final ConstraintReference extractor;
   private final Evaluation expression;
   
   public DeclarationAllocator(Constraint constraint, Evaluation expression) {      
      this.extractor = new ConstraintReference(constraint);
      this.converter = new DeclarationConverter();
      this.expression = expression;
   }   
   
   public <T extends Value> T validate(Scope scope, String name, int modifiers) throws Exception {
      Type type = extractor.getConstraint(scope);
      Object object = null;
      
//      if(expression != null) {
      if(type != null) {
         object = scope.getModule().getContext().getProvider().create().createShellConstructor(type).invoke(scope, null, null);
      } else {
         object = new Object();
      }
         //         Value value = expression.validate(scope, null);
//         Object original = value.getValue();
//         
//         if(type != null) {
//            object = converter.convert(scope, original, type, name);
//         } else {
//            object = original;
//         }
//      }
      return create(scope, name, object, type, modifiers);
   }
   
   public <T extends Value> T allocate(Scope scope, String name, int modifiers) throws Exception {
      Type type = extractor.getConstraint(scope);
      Object object = null;
      
      if(expression != null) {
         Value value = expression.evaluate(scope, null);
         Object original = value.getValue();
         
         if(type != null) {
            object = converter.convert(scope, original, type, name);
         } else {
            object = original;
         }
      }
      return create(scope, name, object, type, modifiers);
   }
   
   protected <T extends Value> T create(Scope scope, String name, Object value, Type type, int modifiers) throws Exception {
      if(ModifierType.isConstant(modifiers)) {
         return (T)Local.getConstant(value, name, type);
      }
      return (T)Local.getReference(value, name, type);
   }
}