package org.snapscript.tree.function;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Identity;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.tree.Argument;
import org.snapscript.tree.collection.CollectionIndex;

public class ResultIndex implements Evaluation {
   
   private final Argument argument;
  
   public ResultIndex(Argument argument) {     
      this.argument = argument;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Evaluation value = new Identity(left);
      CollectionIndex index = new CollectionIndex(value, argument);
      
      return index.evaluate(scope, left);
   }
}
