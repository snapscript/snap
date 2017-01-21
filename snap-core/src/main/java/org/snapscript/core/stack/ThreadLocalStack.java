package org.snapscript.core.stack;

public class ThreadLocalStack extends ThreadLocal<TraceStack> {
   
   @Override
   public TraceStack initialValue() {
      return new TraceStack();
   }
}