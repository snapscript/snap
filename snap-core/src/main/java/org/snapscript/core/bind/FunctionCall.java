package org.snapscript.core.bind;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;
import org.snapscript.core.stack.ThreadStack;

public class FunctionCall {
   
   private final Invocation invocation;
   private final Function function;
   
   public FunctionCall(Function function, ThreadStack stack) {
      this.invocation = new StackInvocation(function, stack);
      this.function = function;
   }
   
   public Function getFunction() {
      return function;
   }
   
   public Invocation getInvocation() {
      return invocation;
   }
   
   @Override
   public String toString() {
      return String.valueOf(function);
   }
   
   private static class StackInvocation implements Invocation {
   
      private final ThreadStack stack;
      private final Function function;
      
      public StackInvocation(Function function, ThreadStack stack) {
         this.function = function;
         this.stack = stack;
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... arguments) throws Exception{
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
   
}