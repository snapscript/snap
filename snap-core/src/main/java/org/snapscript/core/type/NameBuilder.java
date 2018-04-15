package org.snapscript.core.type;

import org.snapscript.core.InternalArgumentException;

public class NameBuilder {
   
   private static final String[] DIMENSIONS = {"", "[]", "[][]", "[][][]" };     
   private static final String DIMENSION = "[]";
   
   public NameBuilder(){
      super();
   }
   
   public String createFullName(Class type) {
      Class entry = type.getComponentType();
      
      if(entry != null) {
         return createFullName(entry) + DIMENSION;
      }
      return type.getName();
   }
   
   public String createShortName(Class type) {
      Class entry = type.getComponentType();
      
      if(entry != null) {
         return createShortName(entry) + DIMENSION;
      }
      String name = type.getName();
      int index = name.lastIndexOf('.');
      int length = name.length();
      
      if(index > 0) {
         return name.substring(index+1, length);
      }
      return name;
   }
   
   public String createShortName(String type) {
      int index = type.lastIndexOf('.');
      int length = type.length();
      
      if(index > 0) {
         return type.substring(index+1, length);
      }
      return type;
   }
   
   public String createFullName(String module, String name) {
      if(module == null) { // is a null module legal?
         return name;
      }
      if(name == null) {
         return module;
      }
      return module + "." + name;
   }
   
   public String createArrayName(String module, String name, int size) {
      int limit = DIMENSIONS.length;
      
      if(size >= DIMENSIONS.length) {
         throw new InternalArgumentException("Maximum of " + limit + " dimensions exceeded");
      }
      String bounds = DIMENSIONS[size];
      
      if(module != null) { // is a null module legal?
         return createFullName(module, name) + bounds;
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
               return createFullName(module, parent);
            }
         }
      }
      return null;
   }
   
   public String createLocalName(String type) {
      if(type != null) {
         String name = createTopName(type);
         int index = name.lastIndexOf('.');
         int length = name.length();
         
         if(index > 0) {
            return name.substring(index+1, length);
         }
         return name;
      }
      return null;
   }
   
   public String createTopName(String type) {
      if(type != null) {
         int index = type.indexOf('$');
         
         if(index > 0) {
            return type.substring(0, index);
         }
         return type;
      }
      return null;
   }
   
   public String createTopName(String module, String name) {
      if(name != null) {
         int index = name.indexOf('$');
         
         if(index > 0) {
            String parent = name.substring(0, index);
            int length = parent.length();
            
            if(length > 0) {
               return createFullName(module, parent);
            }
         }
         return createFullName(module, name);
      }
      return null;
   }
}