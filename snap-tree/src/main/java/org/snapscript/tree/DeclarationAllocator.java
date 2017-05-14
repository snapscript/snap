
package org.snapscript.tree;

import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
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

   public Value allocate(Scope scope, String name, int modifiers) throws Exception {
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
      return create(scope, object, type, modifiers);
   }
   
   protected Value create(Scope scope, Object value, Type type, int modifiers) throws Exception {
      if(ModifierType.isConstant(modifiers)) {
         return ValueType.getConstant(value, type);
      }
      return ValueType.getReference(value, type);
   }
}
