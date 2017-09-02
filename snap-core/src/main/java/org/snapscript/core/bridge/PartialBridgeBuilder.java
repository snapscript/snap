package org.snapscript.core.bridge;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.function.Invocation;

public class PartialBridgeBuilder implements BridgeBuilder {
   
   public PartialBridgeBuilder() {
      super();
   }

   @Override
   public Invocation superConstructor(Type type, Type real) {
      throw new IllegalStateException("Could not create '" + real + "' super constructor");
   }

   @Override
   public Invocation superMethod(Type type, Method method) {
      throw new IllegalStateException("Could not create " + method + " super method");
   }

   @Override
   public Invocation thisMethod(Type type, Method method) {
      return new DelegateMethodInvocation(method);
   }

   @Override
   public Invocation thisConstructor(Type type, Constructor constructor) {
      return new DelegateConstructorInvocation(constructor);
   } 
   
   public class DelegateMethodInvocation implements Invocation {
      
      private final Method method;
      
      public DelegateMethodInvocation(Method method) {
         this.method = method;
      }

      @Override
      public Result invoke(Scope scope, Object value, Object... arguments) {
         try {
            Object result = method.invoke(value, arguments);
            return ResultType.getNormal(result);
         }catch(Throwable e) {
            throw new InternalStateException("Could not invoke method " + method, e);
         }
      }
   }
   
   public class DelegateConstructorInvocation implements Invocation {
      
      private final Constructor constructor;
      
      public DelegateConstructorInvocation(Constructor constructor) {
         this.constructor = constructor;
      }

      @Override
      public Result invoke(Scope scope, Object value, Object... arguments) {
         try {
            Object result = constructor.newInstance(arguments);
            return ResultType.getNormal(result);
         }catch(Throwable e) {
            throw new InternalStateException("Could not invoke constructor " + constructor, e);
         }
      }
   }
}
