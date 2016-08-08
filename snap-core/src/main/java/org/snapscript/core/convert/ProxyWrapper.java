package org.snapscript.core.convert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.snapscript.core.Any;
import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.function.Function;

public class ProxyWrapper {

   private final ProxyFactory factory;
   
   public ProxyWrapper(Context context) {
      this.factory = new ProxyFactory(this, context);
   }
   
   public Object toProxy(Object object) { 
      return toProxy(object, Any.class);
   }
   
   public Object toProxy(Object object, Class require) { 
      return toProxy(object, require, Any.class);
   }
   
   public Object toProxy(Object object, Class... require) { 
      if(object != null) {
         if(Scope.class.isInstance(object)) {
            Object proxy = factory.create((Scope)object, require);
            
            for(Class type : require) {
               if(!type.isInstance(proxy)) {
                  throw new InternalStateException("Proxy does not implement " + require);
               }
            }
            return proxy;
         }
         if(Function.class.isInstance(object)) {
            Object proxy = factory.create((Function)object, require);
            
            for(Class type : require) {
               if(!type.isInstance(proxy)) {
                  throw new InternalStateException("Proxy does not implement " + require);
               }
            }
            return proxy;
         }
      }
      return object;
   }
   
   public Object fromProxy(Object object) {
      if(object != null) {
         if(Proxy.class.isInstance(object)) {
            InvocationHandler handler = Proxy.getInvocationHandler(object);
           
            if(ScopeProxyHandler.class.isInstance(handler)) {
               ScopeProxyHandler proxy = (ScopeProxyHandler)handler;
               Object value = proxy.extract();
               
               return value;
            }
            if(FunctionProxyHandler.class.isInstance(handler)) {
               FunctionProxyHandler proxy = (FunctionProxyHandler)handler;
               Object value = proxy.extract();
               
               return value;
            }
         }
      }
      return object;
   }
}
