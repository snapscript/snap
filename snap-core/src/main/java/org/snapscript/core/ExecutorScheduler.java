package org.snapscript.core;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.common.Consumer;
import org.snapscript.core.error.InternalStateException;

public class ExecutorScheduler implements TaskScheduler {

   private final Executor executor;

   public ExecutorScheduler(Executor executor) {
      this.executor = executor;
   }

   @Override
   public <T> Promise<T> schedule(Consumer<Object, T> consumer) {
      FuturePromise<T> promise = new FuturePromise<T>(consumer);

      if(consumer != null) {
         promise.execute(executor);
      }
      return promise;
   }

   private static class FuturePromise<T> implements Promise<T> {

      private final Set<Consumer<T, Object>> consumers;
      private final AtomicReference<T> result;
      private final FutureTask<T> future;
      private final Callable<T> task;

      public FuturePromise(Consumer<Object, T> consumer) {
         this.consumers = new CopyOnWriteArraySet<Consumer<T, Object>>();
         this.task = new PromiseTask<T>(this, consumer);
         this.result = new AtomicReference<T>();
         this.future = new FutureTask<T>(task);
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

      public void execute(Executor executor) {
         if(executor != null) {
            executor.execute(future);
         } else {
            future.run();
         }
      }

      public void complete(T value) {
         for(Consumer<T, Object> consumer : consumers) {
            consumer.consume(value);
         }
         result.set(value);
      }

      @Override
      public Promise<T> then(Consumer<T, Object> consumer) {
         T value = result.get();

         if(value != null) {
            consumer.consume(value);
         }
         consumers.add(consumer);
         return this;
      }
   }


   private static class PromiseTask<T> implements Callable<T> {

      private final Consumer<Object, T> consumer;
      private final FuturePromise<T> promise;

      public PromiseTask(FuturePromise<T> promise, Consumer<Object, T> consumer) {
         this.consumer = consumer;
         this.promise = promise;
      }

      @Override
      public T call() {
         T value = consumer.consume(null);
         promise.complete(value);
         return value;
      }
   }
}
