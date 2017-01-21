package org.snapscript.core.stack;

import org.snapscript.common.Stack;
import org.snapscript.core.function.Function;

public class ThreadStack {
   
   private final StackTraceBuilder builder;
   private final ThreadLocalStack local;
   
   public ThreadStack() {
      this.builder = new StackTraceBuilder();
      this.local = new ThreadLocalStack();
   }
   
   public Function current() {
      Stack stack = local.get();
      
      for(Object entry : stack) {
         if(Function.class.isInstance(entry)) {
            return (Function)entry;
         }
      }
      return null;
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
