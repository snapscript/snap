package org.snapscript.core;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.common.Consumer;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.variable.Value;

public class ExecutorScheduler implements TaskScheduler {

   private final Executor executor;

   public ExecutorScheduler(Executor executor) {
      this.executor = executor;
   }

   @Override
   public <T> Promise<T> schedule(Callable<T> task) {
      PromiseFuture<T> promise = new PromiseFuture<T>(task);

      if(task != null) {
         promise.execute(executor);
      }
      return promise;
   }

   private static class PromiseFuture<T> implements Promise<T> {

      private final PromiseDispatcher<T> dispatcher;
      private final PromiseExecutor<T> executor;
      private final FutureTask<T> future;

      public PromiseFuture(Callable<T> task) {
         this.dispatcher = new PromiseDispatcher<T>();
         this.executor = new PromiseExecutor<T>(dispatcher, task);
         this.future = new FutureTask<T>(executor);
      }

      @Override
      public T get() {
         try {
            return (T)future.get();
         } catch(Exception e) {
            throw new InternalStateException("Could not get value", e);
         }
      }

      @Override
      public T get(long wait) {
         try {
            return (T)future.get(wait, MILLISECONDS);
         } catch(Exception e) {
            throw new InternalStateException("Could not get value", e);
         }
      }

      @Override
      public Promise<T>  join() {
         try {
            future.get();
         } catch(Exception e) {
            return this;
         }
         return this;
      }

      @Override
      public Promise<T>  join(long wait) {
         try {
            future.get(wait, MILLISECONDS);
         } catch(Exception e) {
            return this;
         }
         return this;
      }

      @Override
      public Promise<T> thenCatch(Consumer<Throwable, Object> consumer) {
         if(consumer != null) {
            dispatcher.thenCatch(consumer);
            dispatcher.error();
         }
         return this;
      }

      @Override
      public Promise<T> thenAccept(Consumer<T, Object> consumer) {
         if(consumer != null) {
            dispatcher.thenAccept(consumer);
            dispatcher.complete();
         }
         return this;
      }

      public void execute(Executor executor) {
         if(executor != null) {
            executor.execute(future);
         } else {
            future.run();
         }
      }
   }

   private static class PromiseDispatcher<T> {

      private final BlockingQueue<Consumer<Throwable, Object>> failures;
      private final BlockingQueue<Consumer<T, Object>> listeners;
      private final AtomicReference<Throwable> error;
      private final AtomicReference<Value> success;

      public PromiseDispatcher() {
         this.failures = new LinkedBlockingQueue<Consumer<Throwable, Object>>();
         this.listeners = new LinkedBlockingQueue<Consumer<T, Object>>();
         this.error = new AtomicReference<Throwable>();
         this.success = new AtomicReference<Value>();
      }

      public void complete() {
         Value value = success.get();

         if (value != null) {
            T object = value.getValue();

            while (!listeners.isEmpty()) {
               Consumer<T, Object> listener = listeners.poll();

               if (listener != null) {
                  listener.consume(object);
               }
            }
         }
      }

      public void error() {
         Throwable cause = error.get();

         if (cause != null) {
            while (!failures.isEmpty()) {
               Consumer<Throwable, Object> failure = failures.poll();

               if (failure != null) {
                  failure.consume(cause);
               }
            }
         }
      }

      public void thenAccept(Consumer<T, Object> consumer) {
         listeners.add(consumer);
      }

      public void thenCatch(Consumer<Throwable, Object> consumer) {
         failures.add(consumer);
      }

      public void result(Value value) {
         success.compareAndSet(null, value);
      }

      public void exception(Throwable cause) {
         error.compareAndSet(null, cause);
      }
   }

   private static class PromiseExecutor<T> implements Callable<T> {

      private final PromiseDispatcher<T> dispatcher;
      private final Callable<T> task;

      public PromiseExecutor(PromiseDispatcher<T> dispatcher, Callable<T> task) {
         this.dispatcher = dispatcher;
         this.task = task;
      }

      @Override
      public T call() throws Exception {
         try {
            T result = task.call();
            Value value = Value.getTransient(result);

            dispatcher.result(value);
            dispatcher.complete();

            return result;
         } catch(Exception cause) {
            dispatcher.exception(cause);
            dispatcher.error();

            throw cause;
         }
      }
   }

}
