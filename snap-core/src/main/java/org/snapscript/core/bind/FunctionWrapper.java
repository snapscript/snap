package org.snapscript.core.bind;

import org.snapscript.core.function.Function;
import org.snapscript.core.stack.ThreadStack;

public class FunctionWrapper {
   
   private final ThreadStack stack;
   
   public FunctionWrapper(ThreadStack stack) {
      this.stack = stack;
   }
   
   public FunctionCall toCall(Function function) {
      return new FunctionCall(function, stack);
   }

}
