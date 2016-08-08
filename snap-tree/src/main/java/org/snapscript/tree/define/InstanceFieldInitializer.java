package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;

public class InstanceFieldInitializer extends Initializer {
   
   private final Evaluation evaluation;
   
   public InstanceFieldInitializer(Evaluation evaluation){
      this.evaluation = evaluation;
   }

   @Override
   public Result execute(Scope scope, Type type) throws Exception {
      if(evaluation != null) {
         evaluation.evaluate(scope, null); 
      }
      return ResultType.getNormal();
   }
}