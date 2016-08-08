package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public class StaticFieldInitializer extends StaticInitializer {
   
   private final Evaluation evaluation;
   
   public StaticFieldInitializer(Evaluation evaluation){
      this.evaluation = evaluation;
   }

   @Override
   protected Result compile(Type type) throws Exception {
      Scope scope = type.getScope();
      evaluation.evaluate(scope, null);
      return ResultType.getNormal();
   }
}