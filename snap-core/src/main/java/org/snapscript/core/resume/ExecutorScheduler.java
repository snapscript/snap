package org.snapscript.core.resume;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public class ExecutorScheduler implements TaskScheduler {

   private final ErrorHandler handler;
   private final Executor executor;

   public ExecutorScheduler(ErrorHandler handler, Executor executor) {
      this.executor = executor;
      this.handler = handler;
   }

   @Override
   public Promise schedule(Scope scope, Task task) {
      PromiseDelegate promise = new PromiseDelegate(handler, executor, scope, task);

      if(task != null) {
         promise.execute();
      }
      return promise;
   }

   private static class PromiseDelegate implements Promise {

      private final PromiseFuture future;
      private final PromiseAnswer answer;
      private final PromiseTask task;
      private final Executor executor;

      public PromiseDelegate(ErrorHandler handler, Executor executor, Scope scope, Task task) {
         this.future = new PromiseFuture(handler, scope);
         this.answer = new PromiseAnswer(future, handler, scope);
         this.task = new PromiseTask(answer, task);
         this.executor = executor;
      }

      @Override
      public Object value() {
         return future.get();
      }

      @Override
      public Object value(long wait) {
         return future.get(wait, MILLISECONDS);
      }

      @Override
      public Object value(long wait, TimeUnit unit) {
         return future.get(wait, unit);
      }

      @Override
      public Promise join() {
         try {
            future.get();
         } catch(Throwable e){
            return this;
         }
         return this;
      }

      @Override
      public Promise join(long wait) {
         try {
            future.get(wait, MILLISECONDS);
         } catch(Throwable e){
            return this;
         }
         return this;
      }

      @Override
      public Promise join(long wait, TimeUnit unit) {
         try {
            future.get(wait, unit);
         } catch(Throwable e){
            return this;
         }
         return this;
      }

      @Override
      public Promise failure(Task task) {
         if(task != null) {
            future.failure(task);
         }
         return this;
      }

      @Override
      public Promise failure(Runnable task) {
         Task adapter = new RunnableTask(task);

         if(task != null) {
            future.failure(adapter);
         }
         return this;
      }

      @Override
      public Promise success(Task task) {
         if(task != null) {
            future.success(task);
         }
         return this;
      }

      @Override
      public Promise success(Runnable task) {
         Task adapter = new RunnableTask(task);

         if(task != null) {
            future.success(adapter);
         }
         return this;
      }

      public void execute() {
         if(executor != null) {
            executor.execute(task);
         } else {
            task.run();
         }
      }
   }

   private static class PromiseFuture implements Callable {

      private final AtomicReference<Throwable> error;
      private final AtomicReference<Value> success;
      private final BlockingQueue<Task> listeners;
      private final BlockingQueue<Task> failures;
      private final ErrorHandler handler;
      private final FutureTask task;
      private final Scope scope;

      public PromiseFuture(ErrorHandler handler, Scope scope) {
         this.failures = new LinkedBlockingQueue<Task>();
         this.listeners = new LinkedBlockingQueue<Task>();
         this.error = new AtomicReference<Throwable>();
         this.success = new AtomicReference<Value>();
         this.task = new FutureTask(this);
         this.handler = handler;
         this.scope = scope;
      }

      @Override
      public Object call() {
         Value value = success.get();

         if (value != null) {
            return value.getValue();
         }
         return null;
      }

      public Object get() {
         try {
            return task.get();
         } catch(Exception e) {
            return handler.failInternalError(scope, e);
         }
      }

      public Object get(long wait, TimeUnit unit) {
         try {
            return task.get(wait, unit);
         } catch(Exception e) {
            return handler.failInternalError(scope, e);
         }
      }

      public void complete() {
         Value value = success.get();

         if (value != null) {
            Object object = value.getValue();

            while (!listeners.isEmpty()) {
               Task listener = listeners.poll();

               if (listener != null) {
                  listener.execute(object);
               }
            }
         }
      }

      public void error() {
         Throwable cause = error.get();

         if (cause != null) {
            while (!failures.isEmpty()) {
               Task failure = failures.poll();

               if (failure != null) {
                  failure.execute(cause);
               }
            }
         }
      }

      public void success(Task task) {
         if(listeners.add(task)) {
            complete();
         }
      }

      public void failure(Task task) {
         if(failures.add(task)) {
            error();
         }
      }

      public void success(Value value) {
         if(success.compareAndSet(null, value)) {
            task.run();
         }
      }

      public void failure(Throwable cause) {
         if(error.compareAndSet(null, cause)) {
            task.run();
         }
      }
   }

   private static class PromiseAnswer implements Answer {

      private final PromiseFuture future;
      private final ErrorHandler handler;
      private final Scope scope;

      public PromiseAnswer(PromiseFuture future, ErrorHandler handler, Scope scope) {
         this.handler = handler;
         this.future = future;
         this.scope = scope;
      }

      @Override
      public void success(Object result) {
         try {
            Value value = Value.getTransient(result);

            future.success(value);
            future.complete();
         } catch(Exception e) {
            handler.failInternalError(scope, e);
         }
      }

      @Override
      public void failure(Throwable cause) {
         try {
            future.failure(cause);
            future.error();
         } catch(Exception e) {
            handler.failInternalError(scope, e);
         }
      }
   }

   private static class PromiseTask implements Runnable {

      private final PromiseAnswer answer;
      private final Task task;

      public PromiseTask(PromiseAnswer answer, Task task) {
         this.answer = answer;
         this.task = task;
      }

      @Override
      public void run() {
         try {
            task.execute(answer);
         }catch(Exception cause){
            answer.failure(cause);
         }
      }
   }
}
