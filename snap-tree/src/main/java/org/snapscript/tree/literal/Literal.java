package org.snapscript.tree.literal;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public abstract class Literal implements Evaluation {
   
   private volatile Value value;
   
   protected Literal() {
      super();
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
