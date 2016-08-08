package org.snapscript.core.index;

import java.lang.reflect.Method;

public enum PropertyType {
   GET("get", true),
   SET("set", false),      
   IS("is", true);

   private final String prefix;
   private final boolean read;
   private final int size;

   private PropertyType(String prefix, boolean read) {
      this.size = prefix.length();         
      this.prefix = prefix;
      this.read = read;
   }
   
   public boolean isWrite(Method method) {
      String name = method.getName();
      int length = name.length();
      
      if(name.startsWith(prefix)) { // rubbish
         Class type = method.getReturnType();
         Class[] types = method.getParameterTypes();
         int count = types.length;

         if(type == void.class) {
            return length > size && count == 1;
         }
      }
      return false;
   }      

   public boolean isRead(Method method) {
      String name = method.getName();
      int length = name.length();
      
      if(name.startsWith(prefix)) {
         Class type = method.getReturnType();
         Class[] types = method.getParameterTypes();
         int count = types.length;

         if(type != void.class) {
            return length > size && count == 0;
         }
      }
      return false;
   }

   public String getProperty(Method method) {
      String name = method.getName();
      
      if(name.startsWith(prefix)) {
         return PropertyNameExtractor.getProperty(name, prefix);
      }
      return name;
   }
}
