package org.snapscript.core.function.find;

import org.snapscript.core.function.Function;
import org.snapscript.core.stack.ThreadStack;

public class FunctionWrapper {
   
   private final ThreadStack stack;
   
   public FunctionWrapper(ThreadStack stack) {
      this.stack = stack;
   }
   
   public FunctionPointer toCall(Function function) {
      return new FunctionPointer(function, stack);
   }

}