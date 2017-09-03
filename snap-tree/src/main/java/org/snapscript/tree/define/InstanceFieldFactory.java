package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;

public class InstanceFieldFactory extends TypeFactory {
   
   private final Evaluation evaluation;
   
   public InstanceFieldFactory(Evaluation evaluation){
      this.evaluation = evaluation;
   }
   
   @Override
   public Result compile(Scope scope, Type type) throws Exception {
      if(evaluation != null) {
         evaluation.compile(scope, null); 
      }
      return Result.getNormal();
   }

   @Override
   public Result execute(Scope scope, Type type) throws Exception {
      if(evaluation != null) {
         evaluation.evaluate(scope, null); 
      }
      return Result.getNormal();
   }
}