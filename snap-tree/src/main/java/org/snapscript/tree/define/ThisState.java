package org.snapscript.tree.define;

import static org.snapscript.core.type.Order.OTHER;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Execution;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.Order;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.result.Result;

public class ThisState extends TypeState {
   
   private final AtomicBoolean allocate;
   private final AtomicBoolean define;
   private final AtomicBoolean compile;
   private final Evaluation expression;
   private final Execution execution;
   
   public ThisState(Execution execution, Evaluation expression) {
      this.define = new AtomicBoolean();
      this.allocate = new AtomicBoolean();
      this.compile = new AtomicBoolean();
      this.expression = expression;
      this.execution = execution;
   }

   @Override
   public Order define(Scope instance, Type real) throws Exception {
      if(define.compareAndSet(false, true)) {
         expression.define(instance);
      }
      return OTHER;
   }
   
   @Override
   public void compile(Scope instance, Type real) throws Exception {
      if(compile.compareAndSet(false, true)) {
         expression.compile(instance, null);
      }
   }   
   
   @Override
   public void allocate(Scope instance, Type real) throws Exception {
      if(allocate.compareAndSet(false, true)) {
         execution.execute(instance);
      }
   }
   
   @Override
   public Result execute(Scope instance, Type real) throws Exception {
      Value value = expression.evaluate(instance, null);
      Instance result = value.getValue();
      
      return Result.getNormal(result); // this will return the instance created!!
   }
}