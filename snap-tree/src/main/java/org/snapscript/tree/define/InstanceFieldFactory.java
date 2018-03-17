package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;

public class InstanceFieldFactory extends TypeFactory {
   
   private final Evaluation evaluation;
   
   public InstanceFieldFactory(Evaluation evaluation){
      this.evaluation = evaluation;
   }
   
   @Override
   public void compile(Scope scope, Type type) throws Exception {
      if(evaluation != null) {
         evaluation.define(scope); 
      }
   }

   @Override
   public void validate(Scope scope, Type type) throws Exception {
      if(evaluation != null) {
         evaluation.compile(scope, null); 
      }
   }
   
   @Override
   public Result execute(Scope scope, Type type) throws Exception {
      if(evaluation != null) {
         evaluation.evaluate(scope, null); 
      }
      return Result.getNormal();
   }
}