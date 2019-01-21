package org.snapscript.tree.resume;

import java.util.Iterator;
import java.util.concurrent.Callable;

import org.snapscript.core.Execution;
import org.snapscript.core.Promise;
import org.snapscript.core.PromiseWrapper;
import org.snapscript.core.TaskScheduler;
import org.snapscript.core.result.Result;
import org.snapscript.core.resume.Yield;
import org.snapscript.core.scope.Scope;

public class AsyncExecution extends Execution {

   private final TaskScheduler scheduler;
   private final PromiseWrapper wrapper;
   private final Execution execution;

   public AsyncExecution(TaskScheduler scheduler, Execution execution) {
      this.wrapper = new PromiseWrapper();
      this.scheduler = scheduler;
      this.execution = execution;
   }

   @Override
   public Result execute(Scope scope) throws Exception {
      Result result = execution.execute(scope);

      if(!result.isAwait()) {
         Object value = result.getValue();
         Promise promise = wrapper.toPromise(scope, value);

         return Result.getNormal(promise);
      }
      return execute(scope, result);
   }

   private Result execute(Scope scope, Result result) throws Exception {
      ResumeExecution execution = new ResumeExecution(result);
      Promise promise = scheduler.schedule(execution);

      return Result.getNormal(promise);
   }

   private static class ResumeExecution implements Callable<Object> {

      private final Result result;

      public ResumeExecution(Result result){
         this.result = result;
      }

      @Override
      public Object call() {
         Yield yield = result.getValue();
         Iterator<Object> iterator = yield.iterator();
         Object result = null;

         while(iterator.hasNext()) {
            result = iterator.next();
         }
         return result;
      }
   }
}
