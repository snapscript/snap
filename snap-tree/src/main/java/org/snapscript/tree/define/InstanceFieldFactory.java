package org.snapscript.tree.define;

import static org.snapscript.core.result.Result.NORMAL;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.result.Result;

public class InstanceFieldFactory extends TypeFactory {
   
   private final Evaluation evaluation;
   
   public InstanceFieldFactory(Evaluation evaluation){
      this.evaluation = evaluation;
   }
   
   @Override
   public void define(Scope scope, Type type) throws Exception {
      if(evaluation != null) {
         evaluation.define(scope); 
      }
   }

   @Override
   public void compile(Scope scope, Type type) throws Exception {
      if(evaluation != null) {
         evaluation.compile(scope, null); 
      }
   }
   
   @Override
   public Result execute(Scope scope, Type type) throws Exception {
      if(evaluation != null) {
         evaluation.evaluate(scope, null); 
      }
      return NORMAL;
   }
}