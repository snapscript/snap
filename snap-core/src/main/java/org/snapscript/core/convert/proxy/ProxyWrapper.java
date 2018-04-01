package org.snapscript.core.convert.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.snapscript.core.Context;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.function.Function;
import org.snapscript.core.platform.Bridge;
import org.snapscript.core.scope.instance.Instance;

public class ProxyWrapper {

   private final ProxyFactory factory;
   
   public ProxyWrapper(Context context) {
      this.factory = new ProxyFactory(this, context);
   }
   
   public Object asProxy(Instance instance) {
      if(instance != null) {
         return factory.create(instance);
      }
      return null;
   }
   
   public Object asProxy(Function function) {
      if(function != null) {
         return factory.create(function, Delegate.class);
      }
      return null;
   }
   
   public Object asProxy(Function function, Class require) {
      if(function != null) {
         return factory.create(function, require, Delegate.class);
      }
      return null;
   }
   
   public Object toProxy(Object object) { 
      if(object != null) {
         if(Instance.class.isInstance(object)) {
            Instance instance = (Instance)object;
            Object proxy = instance.getProxy();
            
            return proxy;
         }
         if(Function.class.isInstance(object)) {
            Function function = (Function)object;
            Object proxy = function.getProxy();
            
            return proxy;
         }
      }
      return object;
   }

   public Object toProxy(Object object, Class require) { 
      if(object != null) {
         if(Instance.class.isInstance(object)) {
            Instance instance = (Instance)object;
            Object bridge = instance.getBridge();
               
            if(require.isInstance(bridge)) {
               return bridge;
            }
            Object proxy = instance.getProxy();
            
            if(!require.isInstance(proxy)) {
               throw new InternalStateException("Type does not extend or implement " + require);
            }
            return proxy;
         }
         if(Function.class.isInstance(object)) {
            Function function = (Function)object;
            Object proxy = function.getProxy(require);
            
            if(!require.isInstance(proxy)) {
               throw new InternalStateException("Type does not implement " + require);
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
           
            if(ProxyHandler.class.isInstance(handler)) {
               ProxyHandler proxy = (ProxyHandler)handler;
               Object value = proxy.extract();
               
               return value;
            }
            return object;
         }
         if(Bridge.class.isInstance(object)) {
            Bridge bridge = (Bridge)object;
            Object value = bridge.getInstance();
            
            return value;
         }
      }
      return object;
   }
}