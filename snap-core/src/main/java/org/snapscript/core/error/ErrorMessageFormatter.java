package org.snapscript.core.error;

import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;
import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;

public class ErrorMessageFormatter {
   
   private final TypeExtractor extractor;
   
   public ErrorMessageFormatter(TypeExtractor extractor) {
      this.extractor = extractor;
   }
   
   public String formatFunction(String name, Object[] list) {
      if(name.equals(TYPE_CONSTRUCTOR)) {
         return formatFunction(name, list, 1);
      } 
      return formatFunction(name, list, 0);
   }
   
   public String formatFunction(String name, Type[] list) {
      if(name.equals(TYPE_CONSTRUCTOR)) {
         return formatFunction(name, list, 1);
      } 
      return formatFunction(name, list, 0);
   }
   
   private String formatFunction(String name, Type[] list, int start) {
      StringBuilder builder = new StringBuilder();
      
      builder.append(name);
      builder.append("(");

      for(int i = start; i < list.length; i++) {
         Type type = list[i];

         if(i > start) {
            builder.append(", ");
         }
         if(type != null) {
            builder.append(type);
         } else {
            builder.append(DEFAULT_PACKAGE);
            builder.append(".");
            builder.append(ANY_TYPE);
         }
      }
      builder.append(")");
      
      return builder.toString();      
   }
   
   private String formatFunction(String name, Object[] list, int start) {
      StringBuilder builder = new StringBuilder();
      
      builder.append(name);
      builder.append("(");

      for(int i = start; i < list.length; i++) {
         Object value = list[i];
         Type parameter = extractor.getType(value);
         
         if(i > start) {
            builder.append(", ");
         }
         if(parameter != null) {
            builder.append(parameter);
         } else {
            builder.append(DEFAULT_PACKAGE);
            builder.append(".");
            builder.append(ANY_TYPE);
         }
      }
      builder.append(")");
      
      return builder.toString();
   }
}
