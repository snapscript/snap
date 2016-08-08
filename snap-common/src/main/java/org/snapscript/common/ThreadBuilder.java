package org.snapscript.common;

import java.util.concurrent.ThreadFactory;

public class ThreadBuilder implements ThreadFactory {   
   
   private final boolean daemon;   
   
   public ThreadBuilder() {
      this(true);
   }   

   public ThreadBuilder(boolean daemon) {
      this.daemon = daemon;
   }
   
   @Override
   public Thread newThread(Runnable task) {
      Thread thread = new Thread(task);
      
      if(task != null) {
         String name = createName(task, thread);
         
         thread.setDaemon(daemon);
         thread.setName(name);
      }
      return thread;
   }

   public Thread newThread(Runnable task, Class type) {
      Thread thread = new Thread(task);
      
      if(task != null) {
         String name = createName(type, thread);
         
         thread.setDaemon(daemon);
         thread.setName(name);
      }
      return thread;
   }
   
   private String createName(Runnable task, Thread thread) {
      Class type = task.getClass();
      String prefix = type.getSimpleName();      
      String name = thread.getName();

      return String.format("%s: %s", prefix, name);
   }
   
   private String createName(Class type, Thread thread) {
      String prefix = type.getSimpleName();
      String name = thread.getName();

      return String.format("%s: %s", prefix, name);
   }
}
