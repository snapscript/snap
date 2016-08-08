package org.snapscript.tree.constraint;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;

public class ConstraintExtractor {
   
   private final Constraint constraint;
   
   public ConstraintExtractor(Constraint constraint) {
      this.constraint = constraint;
   }

   public Type extract(Scope scope) throws Exception {
      if(constraint != null) {
         Value value = constraint.evaluate(scope, null);
         
         if(value != null) {
            return value.getValue();
         }
      }
      return null;
   }
}
