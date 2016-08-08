package org.snapscript.core.error;

import org.snapscript.common.Stack;

public class ThreadStack {
   
   private final StackTraceBuilder builder;
   private final ThreadLocalStack local;
   
   public ThreadStack() {
      this.builder = new StackTraceBuilder();
      this.local = new ThreadLocalStack();
   }
   
   public StackTraceElement[] build() {
      return build(null);
   }
   
   public StackTraceElement[] build(Throwable cause) {
      Stack stack = local.get();
      
      if(cause != null) {
         return builder.create(stack, cause);   
      }
      return builder.create(stack);
   }
   
   public void before(Object trace) {
      Stack stack = local.get();
      
      if(trace != null) {
         stack.push(trace);
      }
   }
   
   public void after(Object trace) { // remove from stack
      Stack stack = local.get();
      int size = stack.size();
      
      while(!stack.isEmpty()) {
         Object next = stack.pop();
         
         if(next == trace) {
            break;
         }
      }
   }
   
   public void clear() {
      Stack stack = local.get();
      stack.clear();
   }

}
