
package org.snapscript.core.convert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.bridge.Bridge;
import org.snapscript.core.define.Instance;
import org.snapscript.core.function.Function;

public class ProxyWrapper {

   private final ProxyFactory factory;
   
   public ProxyWrapper(Context context) {
      this.factory = new ProxyFactory(this, context);
   }
   
   public Object toProxy(Object object) { 
      if(object != null) {
         if(Scope.class.isInstance(object)) {
            return factory.create((Scope)object);
         }
         if(Function.class.isInstance(object)) {
            return factory.create((Function)object, Delegate.class);
         }
      }
      return object;
   }

   public Object toProxy(Object object, Class require) { 
      if(object != null) {
         if(Instance.class.isInstance(object)) {
            Object proxy = ((Instance)object).getBridge();
               
            if(require.isInstance(proxy)) {
               return proxy;
            }
            if(!require.isInterface()) {
               throw new InternalStateException("Type does not extend " + require);
            }
         }
         if(Scope.class.isInstance(object)) {
            Object proxy = factory.create((Scope)object);
            
            if(!require.isInstance(proxy)) {
               throw new InternalStateException("Type does not implement " + require);
            }
            return proxy;
         }
         if(Function.class.isInstance(object)) {
            Object proxy = factory.create((Function)object, require, Delegate.class);
            
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
         }
         if(Bridge.class.isInstance(object)) {
            Bridge bridge = (Bridge)object;
            Object value = bridge.extract();
            
            return value;
         }
      }
      return object;
   }
}
