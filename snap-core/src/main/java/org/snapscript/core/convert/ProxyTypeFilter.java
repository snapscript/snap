package org.snapscript.core.convert;

import java.lang.reflect.Proxy;

import org.snapscript.core.Type;

public class ProxyTypeFilter {

   public ProxyTypeFilter() {
      super();
   }
   
   public boolean accept(Type type) {
      Class real = type.getType();
      
      if(real != null) { 
         return !Proxy.class.isAssignableFrom(real);
      }
      return true; // null is valid
   }
}