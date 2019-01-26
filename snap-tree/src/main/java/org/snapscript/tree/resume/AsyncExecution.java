package org.snapscript.tree.resume;

import java.util.Iterator;

import org.snapscript.core.Answer;
import org.snapscript.core.Bug;
import org.snapscript.core.Execution;
import org.snapscript.core.Promise;
import org.snapscript.core.PromiseWrapper;
import org.snapscript.core.Task;
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

   @Bug("clean this up")
   private Result execute(Scope scope, Result result) throws Exception {
      Yield yield = result.getValue();
      Iterator<Object> iterator = yield.iterator();
      Task<Answer> task = new AnswerTask(iterator);
      Promise promise = scheduler.schedule(task);

      return Result.getNormal(promise);
   }

   private static class AnswerTask implements Task<Answer> {

      private final Iterator<Object> iterator;

      public AnswerTask(Iterator<Object> iterator) {
         this.iterator = iterator;
      }

      @Override
      public void execute(Answer answer) {
         Task<Object> task = new ResumeTask(iterator, answer);

         try {
            task.execute(null);
         } catch(Exception e){
            answer.failure(e);
         }
      }
   }

   private static class ResumeTask implements Task<Object> {

      private final Iterator<Object> iterator;
      private final Answer answer;

      public ResumeTask(Iterator<Object> iterator, Answer answer) {
         this.iterator = iterator;
         this.answer = answer;
      }

      @Override
      public void execute(Object value) {
         Object object = null;

         try {
            while (iterator.hasNext()) {
               object = iterator.next();

               if (Promise.class.isInstance(object)) {
                  Promise promise = (Promise) object;
                  promise.success(this);
                  return;
               }
            }
            answer.success(object);
         } catch(Exception cause){
            answer.failure(cause);
         }
      }
   }
}
