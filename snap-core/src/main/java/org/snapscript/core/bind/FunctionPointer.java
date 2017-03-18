
package org.snapscript.core.bind;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;
import org.snapscript.core.stack.ThreadStack;

public class FunctionPointer {
   
   private final ThreadStack stack;
   private final Function function;
   private final Object[] arguments;
   
   public FunctionPointer(Function function, ThreadStack stack, Object[] arguments) {
      this.arguments = arguments;
      this.function = function;
      this.stack = stack;
   }
   
   public Result call(Scope scope, Object object) throws Exception{
      Signature signature = function.getSignature();
      ArgumentConverter converter = signature.getConverter();
      Object[] list = converter.convert(arguments);
      Invocation invocation = function.getInvocation();
      Type type = function.getType();
      
      try {
         if(type != null) {
            stack.before(function);
         }
         return invocation.invoke(scope, object, list);
      } finally {
         if(type != null) {
            stack.after(function);
         }
      }
   }
}
