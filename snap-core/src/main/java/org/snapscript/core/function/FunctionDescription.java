package org.snapscript.core.function;

import org.snapscript.core.module.Module;
import org.snapscript.core.type.Type;

public class FunctionDescription {

   private final SignatureDescription description;
   private final String name;
   private final Type type;
   
   public FunctionDescription(Signature signature, Type type, String name) {
      this(signature, type, name, 0);
   }
  
   public FunctionDescription(Signature signature, Type type, String name, int start) {
      this.description = new SignatureDescription(signature, start);
      this.name = name;
      this.type = type;
   }
   
   public String getDescription() {
      StringBuilder builder = new StringBuilder();
      
      if(type != null) {
         String name = type.getName();
         Module module = type.getModule();
         String prefix = module.getName();
         
         builder.append(prefix);
         builder.append(".");
         
         if(name != null) {
            builder.append(name);
            builder.append(".");
         }
      } 
      builder.append(name);
      builder.append(description);
      
      return builder.toString();
   }
   
   @Override
   public String toString() {
      return getDescription();
   }
}