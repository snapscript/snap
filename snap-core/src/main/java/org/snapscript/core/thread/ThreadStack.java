package org.snapscript.core.thread;

import static org.snapscript.core.trace.TraceType.SCOPE;

import org.snapscript.common.Stack;
import org.snapscript.core.function.Function;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceType;

public class ThreadStack {
   
   private final StackTraceBuilder builder;
   private final ThreadLocalStack local;
   
   public ThreadStack() {
      this.builder = new StackTraceBuilder();
      this.local = new ThreadLocalStack();
   }
   
   public ThreadState state() {
      ThreadContext context = local.get();
      return context.getState();
   }
   
   public Function call() {
      ThreadContext context = local.get();
      Stack stack = context.getStack();
      
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
      ThreadContext context = local.get();
      Stack stack = context.getStack();
      
      if(cause != null) {
         return builder.create(stack, cause);   
      }
      return builder.create(stack);
   }
   
   public void before(Function function) {
      ThreadContext context = local.get();
      ThreadState state = context.getState();
      Stack stack = context.getStack();

      stack.push(function);
      state.mark(false); // not visible
   }
   
   public void before(Trace trace) {
      ThreadContext context = local.get();
      TraceType type = trace.getType();
      ThreadState state = context.getState();
      Stack stack = context.getStack();
      
      if(type == SCOPE) {
         state.mark(true); // visible area
      }
      stack.push(trace); 
   }
   
   public void after(Function function) {
      ThreadContext context = local.get();
      ThreadState state = context.getState();
      Stack stack = context.getStack();

      while(!stack.isEmpty()) {
         Object next = stack.pop();
         
         if(next == function) {
            break;
         }
      }
      state.reset();
   }
   
   public void after(Trace trace) {
      ThreadContext context = local.get();
      ThreadState state = context.getState();
      TraceType type = trace.getType();
      Stack stack = context.getStack();

      while(!stack.isEmpty()) {
         Object next = stack.pop();
         
         if(next == trace) {
            break;
         }
      }     
      if(type == SCOPE) {
         state.reset();
      }
   }
   
   public void clear() {
      ThreadContext context = local.get();
      ThreadState state = context.getState();
      Stack stack = context.getStack();
      
      state.clear();
      stack.clear();
   }

}
