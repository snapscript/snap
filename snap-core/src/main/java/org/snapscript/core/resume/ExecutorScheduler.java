package org.snapscript.core.resume;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.variable.Value;

public class ExecutorScheduler implements TaskScheduler {

   private final Executor executor;

   public ExecutorScheduler(Executor executor) {
      this.executor = executor;
   }

   @Override
   public Promise schedule(Task task) {
      PromiseFuture promise = new PromiseFuture(executor, task);

      if(task != null) {
         promise.execute();
      }
      return promise;
   }

   private static class PromiseFuture implements Promise {

      private final PromiseDispatcher dispatcher;
      private final PromiseAnswer answer;
      private final PromiseTask task;
      private final FutureTask future;
      private final Executor executor;

      public PromiseFuture(Executor executor, Task task) {
         this.dispatcher = new PromiseDispatcher();
         this.future = new FutureTask(dispatcher);
         this.answer = new PromiseAnswer(dispatcher, future);
         this.task = new PromiseTask(answer, task);
         this.executor = executor;
      }

      @Override
      public Object get() {
         try {
            return future.get();
         } catch(Exception e) {
            throw new InternalStateException("Could not get value", e);
         }
      }

      @Override
      public Object get(long wait) {
         try {
            return future.get(wait, MILLISECONDS);
         } catch(Exception e) {
            throw new InternalStateException("Could not get value", e);
         }
      }

      @Override
      public Object get(long wait, TimeUnit unit) {
         try {
            return future.get(wait, unit);
         } catch(Exception e) {
            throw new InternalStateException("Could not get value", e);
         }
      }

      @Override
      public Promise join() {
         try {
            future.get();
         } catch(Exception e) {
            return this;
         }
         return this;
      }

      @Override
      public Promise join(long wait) {
         try {
            future.get(wait, MILLISECONDS);
         } catch(Exception e) {
            return this;
         }
         return this;
      }

      @Override
      public Promise join(long wait, TimeUnit unit) {
         try {
            future.get(wait, unit);
         } catch(Exception e) {
            return this;
         }
         return this;
      }

      @Override
      public Promise failure(Task task) {
         if(task != null) {
            dispatcher.failure(task);
         }
         return this;
      }

      @Override
      public Promise failure(Runnable task) {
         Task adapter = new RunnableTask(task);

         if(task != null) {
            dispatcher.failure(adapter);
         }
         return this;
      }

      @Override
      public Promise success(Task task) {
         if(task != null) {
            dispatcher.success(task);
         }
         return this;
      }

      @Override
      public Promise success(Runnable task) {
         Task adapter = new RunnableTask(task);

         if(task != null) {
            dispatcher.success(adapter);
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

   private static class PromiseDispatcher implements Callable {

      private final AtomicReference<Throwable> error;
      private final AtomicReference<Value> success;
      private final BlockingQueue<Task> listeners;
      private final BlockingQueue<Task> failures;

      public PromiseDispatcher() {
         this.failures = new LinkedBlockingQueue<Task>();
         this.listeners = new LinkedBlockingQueue<Task>();
         this.error = new AtomicReference<Throwable>();
         this.success = new AtomicReference<Value>();
      }

      @Override
      public Object call() {
         Value value = success.get();

         if (value != null) {
            return value.getValue();
         }
         return null;
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
         success.compareAndSet(null, value);
      }

      public void failure(Throwable cause) {
         error.compareAndSet(null, cause);
      }
   }

   private static class PromiseAnswer implements Answer {

      private final PromiseDispatcher dispatcher;
      private final FutureTask task;

      public PromiseAnswer(PromiseDispatcher dispatcher, FutureTask task) {
         this.dispatcher = dispatcher;
         this.task = task;
      }

      @Override
      public void success(Object result) {
         try {
            Value value = Value.getTransient(result);

            dispatcher.success(value);
            task.run();
            dispatcher.complete();
         } catch(Exception cause) {
            dispatcher.failure(cause);
            dispatcher.error();

            throw new InternalStateException("Could not complete task", cause);
         }
      }

      @Override
      public void failure(Throwable cause) {
         try {
            dispatcher.failure(cause);
            task.run();
            dispatcher.error();
         } catch(Exception e) {
            throw new InternalStateException("Could not complete task", cause);
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
         }catch(Throwable cause){
            answer.failure(cause);
         }
      }
   }
}
