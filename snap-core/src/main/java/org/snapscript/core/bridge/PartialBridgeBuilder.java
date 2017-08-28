package org.snapscript.core.bridge;

import java.lang.reflect.Method;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Instance;
import org.snapscript.core.function.Invocation;

public class PartialBridgeBuilder implements BridgeBuilder {
   
   public PartialBridgeBuilder() {
      super();
   }

   @Override
   public Instance superInstance(Scope scope, Type real, Object... list) {
      throw new IllegalStateException("Could not create '" + real + "' super constructor");
   }

   @Override
   public Invocation superInvocation(Scope scope, Class proxy, Method method) {
      throw new IllegalStateException("Could not create " + proxy + " super method");
   }

   @Override
   public Invocation thisInvocation(Scope scope, Method method) {
      return new DelegateInvocation(method);
   }

   public class DelegateInvocation implements Invocation {
      
      private final Method method;
      
      public DelegateInvocation(Method method) {
         this.method = method;
      }

      @Override
      public Result invoke(Scope scope, Object value, Object... arguments) {
         try {
            Object result = method.invoke(value, arguments);
            return ResultType.getNormal(result);
         }catch(Throwable e) {
            throw new InternalStateException("Could not invoke super", e);
         }
      }
   } 
}
