package org.snapscript.core.bridge;

import java.lang.reflect.Constructor;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.common.thread.ThreadBuilder;
import org.snapscript.core.Bug;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.bind.FunctionResolver;
import org.snapscript.core.bind.ObjectFunctionMatcher;
import org.snapscript.core.stack.ThreadStack;

public class PlatformBridgeLoader {
   
   private final PlatformClassLoader loader;
   private final FunctionResolver resolver;
   private final BridgeBuilder builder;
   private final Executor executor;
   
   public PlatformBridgeLoader(TypeExtractor extractor, ThreadStack stack) {
      this.resolver = new ObjectFunctionMatcher(extractor, stack);
      this.builder = new PartialBridgeBuilder();
      this.loader = new PlatformClassLoader();
      this.executor = new BridgeExecutor();
   }

   public BridgeBuilder create(Type type) {
      if(type != null) {
         try {
            Constructor constructor = loader.loadConstructor();
            Object extender = constructor.newInstance(resolver, executor, type);
            
            return (BridgeBuilder)extender;
         }catch(Exception e) {
            return builder; 
         }
      }
      return null;
   }
   
   @Bug("bring in to common")
   private static class BridgeExecutor implements Executor {

      private final BlockingQueue<Runnable> queue;
      private final ThreadFactory factory;
      private final AtomicBoolean alive;
      private final BridgeWorker worker;
      
      public BridgeExecutor() {
         this.queue = new LinkedBlockingQueue<Runnable>();
         this.factory = new ThreadBuilder(true);
         this.worker = new BridgeWorker(queue);
         this.alive = new AtomicBoolean();
      }
      
      @Override
      public void execute(Runnable command) {
         if(alive.compareAndSet(false, true)) {
            Thread thread = factory.newThread(worker);
            thread.start();
         }
         queue.offer(command);
      }
   }
   
   private static class BridgeWorker implements Runnable {
      
      private final BlockingQueue<Runnable> queue;
      
      public BridgeWorker(BlockingQueue<Runnable> queue) {
         this.queue = queue;
      }

      @Override
      public void run() {
         while(true) {
            try {
               Runnable task = queue.take();
               task.run(); 
            } catch(Exception e) {
               throw new IllegalStateException("Thread interrupted", e);
            }
         }
      }
   }
}