package org.snapscript.tree.literal;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;

public abstract class Literal extends Evaluation {
   
   private volatile Value value;
   
   protected Literal() {
      super();
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      if(value == null) {
         value = create(scope);
      }
      return Constraint.getInstance(value.getType(scope));
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      if(value == null) {
         value = create(scope);
      }
      return value;
   }
   
   protected abstract Value create(Scope scope) throws Exception; 

}