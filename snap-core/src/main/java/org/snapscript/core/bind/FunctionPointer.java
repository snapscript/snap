package org.snapscript.core.bind;

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
      if(function == null || function.getInvocation() == null) {
         System.err.println();
      }
      this.arguments = arguments;
      this.function = function;
      this.stack = stack;
   }
   
   public Object call(Scope scope, Object object) throws Exception{
      Signature signature = function.getSignature();
      ArgumentConverter converter = signature.getConverter();
      Invocation invocation = function.getInvocation();
      Object source = signature.getSource();
      Type type = function.getType();
      
      try {
         Object[] list = arguments;
         
         if(type != null) {
            stack.before(function);
         }
         if(source != null) {
            list = converter.convert(arguments);
         } else {
            list = converter.assign(arguments);
         }
         return invocation.invoke(scope, object, list);
      } finally {
         if(type != null) {
            stack.after(function);
         }
      }
   }
   
}