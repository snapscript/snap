package org.snapscript.tree.define;

import org.snapscript.core.Bug;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public class StaticFieldFactory extends StaticFactory {
   
   private final Evaluation evaluation;
   
   public StaticFieldFactory(Evaluation evaluation){
      this.evaluation = evaluation;
   }

   @Bug("do we really need to compile the evaluation here????")
   @Override
   protected Result compile(Type type) throws Exception {
      Scope scope = type.getScope();
      
      evaluation.compile(scope, null);
      evaluation.evaluate(scope, null);
      
      return ResultType.getNormal();
   }
}