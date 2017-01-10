package org.snapscript.core.index;

import org.snapscript.core.InternalArgumentException;

public class TypeNameBuilder {
   
   private static final String[] DIMENSIONS = {"", "[]", "[][]", "[][][]" };     
   private static final String DIMENSION = "[]";
   
   public TypeNameBuilder(){
      super();
   }
   
   public String createName(Class type) {
      Class entry = type.getComponentType();
      
      if(entry != null) {
         return createName(entry) + DIMENSION;
      }
      return type.getName();
   }
   
   public String createShortName(Class type) {
      Class entry = type.getComponentType();
      String name = type.getName();
      
      if(entry != null) {
         return createShortName(entry) + DIMENSION;
      }
      int index = name.lastIndexOf('.');
      int length = name.length();
      
      if(index > 0) {
         return name.substring(index+1, length);
      }
      return name;
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
   
   public String createOuterName(String module, String name) {
      if(name != null) {
         int index = name.lastIndexOf('$');
         
         if(index > 0) {
            String parent = name.substring(0, index);
            int length = parent.length();
            
            if(length > 0) {
               return createName(module, parent);
            }
         }
      }
      return null;
   }
}
