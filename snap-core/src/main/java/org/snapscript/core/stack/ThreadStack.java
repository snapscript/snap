package org.snapscript.core.stack;

import org.snapscript.core.function.Function;
import org.snapscript.core.trace.Trace;

public class ThreadStack {
   
   private final StackTraceBuilder builder;
   private final ThreadLocalStack local;
   
   public ThreadStack() {
      this.builder = new StackTraceBuilder();
      this.local = new ThreadLocalStack();
   }
   
   public Function current() {
      TraceStack stack = local.get();
      return stack.peek();
   }
   
   public StackTraceElement[] build() {
      return build(null);
   }
   
   public StackTraceElement[] build(Throwable cause) {
      TraceStack stack = local.get();
      
      if(cause != null) {
         return builder.create(stack, cause);   
      }
      return builder.create(stack);
   }
   
   public void before(Trace trace) {
      TraceStack stack = local.get();
      
      if(trace != null) {
         stack.push(trace);
      }
   }
   
   public void after(Trace trace) { // remove from stack
      TraceStack stack = local.get();
      
      if(trace != null) {
         stack.pop(trace);
      }
   }
   
   public void before(Function function) {
      TraceStack stack = local.get();
      
      if(function != null) {
         stack.push(function);
      }
   }
   
   public void after(Function function) { // remove from stack
      TraceStack stack = local.get();
      
      if(function != null) {
         stack.pop(function);
      }
   }
   
   public void clear() {
      TraceStack stack = local.get();
      stack.clear();
   }

}
