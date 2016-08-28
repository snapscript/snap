package org.snapscript.core.index;

import org.snapscript.core.InternalArgumentException;

public class TypeNameBuilder {
   
   private static final String[] DIMENSIONS = {"", "[]", "[][]", "[][][]" };     

   public TypeNameBuilder(){
      super();
   }
   
   public String createName(Class type) {
      Class entry = type.getComponentType();
      
      if(entry != null) {
         return createName(entry) + "[]";
      }
      return type.getName();
   }
   
   public String createName(String module, String name) {
      if(module != null) { // is a null module legal?
         int length = module.length();
         
         if(length > 0) {
            return module + "." + name;
         }
      }
      return name;
   }
   
   public String createName(String module, String name, int size) {
      int limit = DIMENSIONS.length;
      
      if(size >= DIMENSIONS.length) {
         throw new InternalArgumentException("Maximum of " + limit + " dimensions exceeded");
      }
      String bounds = DIMENSIONS[size];
      
      if(module != null) { // is a null module legal?
         int length = module.length();
         
         if(length > 0) {
            return module + "." + name + bounds;
         }
      }
      return name + bounds;
   }
}
