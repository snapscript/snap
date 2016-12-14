package org.snapscript.core.thread;

import org.snapscript.common.ArrayStack;
import org.snapscript.common.Stack;

public class ThreadContext {

   private final ThreadState state;
   private final Stack stack;
   
   public ThreadContext(){
      this.state = new ThreadState();
      this.stack = new ArrayStack();
   }
   
   public ThreadState getState() {
      return state;
   }
   
   public Stack getStack(){
      return stack;
   }
}
