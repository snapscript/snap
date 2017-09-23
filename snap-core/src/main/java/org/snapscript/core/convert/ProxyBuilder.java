package org.snapscript.core.convert;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.define.Instance;
import org.snapscript.core.function.Function;

public class ProxyBuilder {
   
   public ProxyBuilder() {
      super();
   }
   
   public Object create(Instance instance) {
      Module module = instance.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      
      return wrapper.asProxy(instance);  
   }
   
   public Object create(Function function) {
      Type type = function.getType();
      Module module = type.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      
      return wrapper.asProxy(function);  
   }
   
   public Object create(Function function, Class require) {
      Type type = function.getType();
      Module module = type.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      
      return wrapper.asProxy(function, require);  
   }
}