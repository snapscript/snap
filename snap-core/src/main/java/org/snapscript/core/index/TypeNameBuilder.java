package org.snapscript.core.index;

public class TypeNameBuilder {

   public TypeNameBuilder(){
      super();
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
}
