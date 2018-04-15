package org.snapscript.tree.define;

import static org.snapscript.core.result.Result.NORMAL;
import static org.snapscript.core.type.Order.INSTANCE;

import org.snapscript.core.Evaluation;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.Order;
import org.snapscript.core.type.Type;
import org.snapscript.core.result.Result;

public class InstanceField extends TypeState {
   
   private final Evaluation evaluation;
   
   public InstanceField(Evaluation evaluation){
      this.evaluation = evaluation;
   }
   
   @Override
   public Order define(Scope scope, Type type) throws Exception {
      if(evaluation != null) {
         evaluation.define(scope); 
      }
      return INSTANCE;
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