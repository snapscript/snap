
package org.snapscript.tree.constraint;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;

public class ConstraintReference {
   
   private final Constraint constraint;
   
   public ConstraintReference(Constraint constraint) {
      this.constraint = constraint;
   }

   public Type getConstraint(Scope scope) throws Exception {
      if(constraint != null) {
         Value value = constraint.evaluate(scope, null);
         
         if(value != null) {
            return value.getValue();
         }
      }
      return null;
   }
}
