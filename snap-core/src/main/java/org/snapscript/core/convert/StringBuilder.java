package org.snapscript.core.convert;

import org.snapscript.core.Context;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;

public class StringBuilder {

   public static String create(Scope scope, Object left) {
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      Object object = wrapper.toProxy(left);
      
      return String.valueOf(object);
   }
   
}