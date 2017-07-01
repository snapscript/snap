
package org.snapscript.core.convert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.snapscript.core.Any;
import org.snapscript.core.Bug;
import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.define.Instance;
import org.snapscript.core.function.Function;

public class ProxyWrapper {

   private final ProxyFactory factory;
   
   public ProxyWrapper(Context context) {
      this.factory = new ProxyFactory(this, context);
   }
   
   public Object toProxy(Object object) { 
      if(Function.class.isInstance(object)) {
         return toProxy(object, Delegate.class);
      }
      return toProxy(object, Any.class);
   }
   
   public Object toProxy(Object object, Class require) { 
      if(Function.class.isInstance(object)) {
         return toProxy(object, require, Delegate.class);
      }
      return toProxy(object, require, Any.class);
   }
   
   @Bug("here we need to create an interface that will have a function such as getReal() on ExtendedInstance")
   public Object toProxy(Object object, Class... require) { 
      if(object != null) {
         if(Instance.class.isInstance(object)) {
            Value value = ((Instance)object).getState().get("real");
            if(value != null){
               return value.getValue();
            }
         }
         if(Scope.class.isInstance(object)) {
            Object proxy = factory.create((Scope)object, require);
            
            for(Class type : require) {
               if(!type.isInstance(proxy)) {
                  throw new InternalStateException("Proxy does not implement " + type);
               }
            }
            return proxy;
         }
         if(Function.class.isInstance(object)) {
            Object proxy = factory.create((Function)object, require);
            
            for(Class type : require) {
               if(!type.isInstance(proxy)) {
                  throw new InternalStateException("Proxy does not implement " + type);
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
           
            if(ProxyHandler.class.isInstance(handler)) {
               ProxyHandler proxy = (ProxyHandler)handler;
               Object value = proxy.extract();
               
               return value;
            }
         }
      }
      return object;
   }
}
