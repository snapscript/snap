package org.snapscript.core.function.index;

import org.snapscript.core.attribute.AttributeResult;
import org.snapscript.core.attribute.AttributeResultBinder;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Origin;
import org.snapscript.core.function.Signature;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.stack.ThreadStack;

public class TracePointer implements FunctionPointer {
   
   private final AttributeResultBinder binder;
   private final Invocation invocation;
   private final Function function;
   
   public TracePointer(Function function, ThreadStack stack) {
      this.binder = new AttributeResultBinder(function);
      this.invocation = new TraceInvocation(function, stack);
      this.function = function;
   }

   @Override
   public ReturnType getType(Scope scope) {
      AttributeResult result = binder.bind(scope);

      if(result == null) {
         throw new InternalStateException("No return type for '" + function + "'");
      }
      return new ReturnType(result, scope);
   }
   
   @Override
   public Function getFunction() {
      return function;
   }
   
   @Override
   public Invocation getInvocation() {
      return invocation;
   }

   @Override
   public boolean isCachable() {
      return false;
   }
   
   @Override
   public String toString() {
      return String.valueOf(function);
   }
   
   private static class TraceInvocation implements Invocation {
   
      private final ThreadStack stack;
      private final Function function;
      
      public TraceInvocation(Function function, ThreadStack stack) {
         this.function = function;
         this.stack = stack;
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... arguments) throws Exception{
         Signature signature = function.getSignature();
         ArgumentConverter converter = signature.getConverter();
         Invocation invocation = function.getInvocation();
         Origin origin = signature.getOrigin();
         
         try {
            Object[] list = arguments;
            
            if(!origin.isSystem()) {
               stack.before(function);
            }
            if(origin.isPlatform()) {
               list = converter.convert(arguments);
            } else {
               list = converter.assign(arguments);
            }
            return invocation.invoke(scope, object, list);
         } finally {
            if(!origin.isSystem()) {
               stack.after(function);
            }
         }
      }
   }
}
