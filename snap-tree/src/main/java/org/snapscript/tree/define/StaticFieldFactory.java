package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public class StaticFieldFactory extends StaticFactory {
   
   private final Evaluation evaluation;
   
   public StaticFieldFactory(Evaluation evaluation){
      this.evaluation = evaluation;
   }

   @Override
   protected Result compile(Type type) throws Exception {
      Scope scope = type.getScope();
      
      evaluation.compile(scope);
      evaluation.evaluate(scope, null);
      
      return Result.getNormal();
   }
}