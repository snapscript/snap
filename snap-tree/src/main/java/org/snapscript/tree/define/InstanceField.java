package org.snapscript.tree.define;

import static org.snapscript.core.Order.INSTANCE;
import static org.snapscript.core.result.Result.NORMAL;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Order;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Allocation;
import org.snapscript.core.result.Result;

public class InstanceField extends Allocation {
   
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