package org.snapscript.core.convert.proxy;

import org.snapscript.core.Context;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.type.Type;
import org.snapscript.core.function.Function;

public class ProxyBuilder {
   
   public ProxyBuilder() {
      super();
   }
   
   public Object create(Instance instance) {
      if(instance != null) {
         Module module = instance.getModule();
         Context context = module.getContext();
         ProxyWrapper wrapper = context.getWrapper();
         
         return wrapper.asProxy(instance);  
      }
      return instance;
   }
   
   public Object create(Function function) {
      Type type = function.getType();
      
      if(type != null) {
         Module module = type.getModule();
         Context context = module.getContext();
         ProxyWrapper wrapper = context.getWrapper();
         
         return wrapper.asProxy(function);
      }
      return function;
   }
   
   public Object create(Function function, Class require) {
      Type type = function.getType();
      
      if(type != null) {
         Module module = type.getModule();
         Context context = module.getContext();
         ProxyWrapper wrapper = context.getWrapper();
         
         return wrapper.asProxy(function, require); 
      }
      return function;
   }
}