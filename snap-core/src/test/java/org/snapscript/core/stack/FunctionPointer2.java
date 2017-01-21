package org.snapscript.core.stack;

import org.snapscript.core.Result;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;

public class FunctionPointer2 {
   
   private final ThreadStack stack;
   private final Function function;
   private final Object[] arguments;
   
   public FunctionPointer2(Function function, ThreadStack stack, Object[] arguments) {
      this.arguments = arguments;
      this.function = function;
      this.stack = stack;
   }
   
   public Result call(Scope2 scope, Object object) throws Exception{
      Signature signature = function.getSignature();
      ArgumentConverter converter = signature.getConverter();
      Object[] list = converter.convert(arguments);
      Invocation invocation = function.getInvocation();
      State2 state = scope.getStack();
      State2 next = state.create(); /// allow stack to grow after invocation!!
      
      try {
         stack.before(function);
         //return invocation.invoke(scope, object, list);
         return null;
      } finally {
         stack.after(function);
         next.clear(); /// clear the stack that was built up!!!
      }
   }
}
