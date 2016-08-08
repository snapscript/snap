package org.snapscript.tree.constraint;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class Constraint implements Evaluation {
   
   private Evaluation constraint;
   private Value type;
   
   public Constraint(Evaluation constraint) {
      this.constraint = constraint;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      if(type == null) {
         Value value = constraint.evaluate(scope, null);
         
         if(value == null) {
            throw new InternalStateException("Could not determine constraint");
         }
         type = value;
      }
      return type;
   }
}
