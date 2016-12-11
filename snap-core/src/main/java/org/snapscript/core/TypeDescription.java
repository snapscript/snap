package org.snapscript.core;

public class TypeDescription {

   private final Type type;
   
   public TypeDescription(Type type) {
      this.type = type;
   }
   
   public String getDescription(){
      StringBuilder builder = new StringBuilder();
      
      if(type != null) {
         String name = type.getName();
         Module module = type.getModule();
         Type outer = type.getOuter();
         
         builder.append(module);
         builder.append(".");
         
         if(outer != null) {
            String prefix = outer.getName();
            
            builder.append(prefix);
            builder.append("$");
         }
         builder.append(name);
      }
      return builder.toString();
   }
   
   public String toString() {
      return getDescription();
   }
}
