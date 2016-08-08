package org.snapscript.common;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ThreadPool implements ScheduledExecutorService {

   private final ScheduledExecutorService executor;
   private final ThreadFactory factory;
   
   public ThreadPool() {
      this(1);
   }
   
   public ThreadPool(int threads) {
      this.factory = new ThreadBuilder();
      this.executor = new ScheduledThreadPoolExecutor(threads, factory);
   }

   @Override
   public void execute(Runnable command) {
      executor.execute(command);
   }

   @Override
   public void shutdown() {
      executor.shutdown();
   }

   @Override
   public List<Runnable> shutdownNow() {
      return executor.shutdownNow();
   }

   @Override
   public boolean isShutdown() {
      return executor.isShutdown();
   }

   @Override
   public boolean isTerminated() {
      return executor.isTerminated();
   }

   @Override
   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      return executor.awaitTermination(timeout, unit);
   }

   @Override
   public <T> Future<T> submit(Callable<T> task) {
      return executor.submit(task);
   }

   @Override
   public <T> Future<T> submit(Runnable task, T result) {
      return executor.submit(task, result);
   }

   @Override
   public Future<?> submit(Runnable task) {
      return executor.submit(task);
   }

   @Override
   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
      return executor.invokeAll(tasks);
   }

   @Override
   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
      return executor.invokeAll(tasks, timeout, unit);
   }

   @Override
   public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
      return executor.invokeAny(tasks);
   }

   @Override
   public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      return executor.invokeAny(tasks, timeout, unit);
   }

   @Override
   public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
      return executor.schedule(command, delay, unit);
   }

   @Override
   public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
      return executor.schedule(callable, delay, unit);
   }

   @Override
   public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
      return executor.scheduleAtFixedRate(command, initialDelay, period, unit);
   }

   @Override
   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
      return executor.scheduleWithFixedDelay(command, initialDelay, delay, unit);
   }

}
