package org.snapscript.core.convert;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.lang.reflect.Proxy;

import org.snapscript.core.Type;
import org.snapscript.core.function.Function;

public class TypeInspector {

   public TypeInspector() {
      super();
   }
   
   public boolean isProxy(Type type) {
      Class real = type.getType();
      
      if(real != null) { 
         return Proxy.class.isAssignableFrom(real);
      }
      return false; // null is valid
   }
   
   public boolean isConstructor(Type type, Function function) {
      Type parent = function.getType();
      String name = function.getName();
      
      if(name.equals(TYPE_CONSTRUCTOR)) {
         return parent == type;
      }
      return false;
   }
   
   
   public boolean isSuperConstructor(Type type, Function function) {
      Type parent = function.getType();
      String name = function.getName();
      
      if(name.equals(TYPE_CONSTRUCTOR)) {
         return parent != type;
      }
      return false;
   }
}