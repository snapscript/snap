package org.snapscript.tree.define;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Bug;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Execution;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.Value;
import org.snapscript.core.define.Instance;

public class ThisFactory extends TypeFactory {
   
   private final AtomicBoolean allocate;
   private final AtomicBoolean define;
   private final AtomicBoolean compile;
   private final Evaluation expression;
   private final Execution execution;
   
   public ThisFactory(Execution execution, Evaluation expression) {
      this.define = new AtomicBoolean();
      this.allocate = new AtomicBoolean();
      this.compile = new AtomicBoolean();
      this.expression = expression;
      this.execution = execution;
   }

   @Override
   public void define(Scope instance, Type real) throws Exception {
      if(define.compareAndSet(false, true)) {
         expression.define(instance);
      }
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