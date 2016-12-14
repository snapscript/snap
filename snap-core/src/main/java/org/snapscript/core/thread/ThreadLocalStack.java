package org.snapscript.core.thread;

public class ThreadLocalStack extends ThreadLocal<ThreadContext> {
   
   @Override
   public ThreadContext initialValue() {
      return new ThreadContext();
   }
   
   
}