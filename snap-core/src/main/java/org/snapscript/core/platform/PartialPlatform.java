package org.snapscript.core.platform;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.shell.ShellFactory;

public class PartialPlatform implements Platform {
   
   private final ShellFactory factory;
   
   public PartialPlatform() {
      this.factory = new ShellFactory();
   }

   @Override
   public Invocation createSuperConstructor(Type type, Type real) {
      throw new IllegalStateException("Could not create '" + real + "' super constructor");
   }

   @Override
   public Invocation createSuperMethod(Type type, Method method) {
      throw new IllegalStateException("Could not create " + method + " super method");
   }

   @Override
   public Invocation createMethod(Type type, Method method) {
      return new DelegateMethodInvocation(method);
   }

   @Override
   public Invocation createConstructor(Type type, Constructor constructor) {
      return new DelegateConstructorInvocation(constructor);
   } 
   
   @Override
   public Invocation createShellConstructor(Type type) {
      return factory.createInvocation(type);
   }
   
   public class DelegateMethodInvocation implements Invocation {
      
      private final Method method;
      
      public DelegateMethodInvocation(Method method) {
         this.method = method;
      }

      @Override
      public Object invoke(Scope scope, Object value, Object... arguments) {
         try {
            return method.invoke(value, arguments);
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
      public Object invoke(Scope scope, Object value, Object... arguments) {
         try {
            return constructor.newInstance(arguments);
         }catch(Throwable e) {
            throw new InternalStateException("Could not invoke constructor " + constructor, e);
         }
      }
   }
}