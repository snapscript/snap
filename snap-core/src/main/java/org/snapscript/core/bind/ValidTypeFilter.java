package org.snapscript.core.bind;

import java.lang.reflect.Proxy;

import org.snapscript.core.Type;

public class ValidTypeFilter {

   public boolean accept(Type type) {
      Class real = type.getType();
      
      if(real != null) { 
         return !Proxy.class.isAssignableFrom(real);
      }
      return true; // null is valid
   }
}
