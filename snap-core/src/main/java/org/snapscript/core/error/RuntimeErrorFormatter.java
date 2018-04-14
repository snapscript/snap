package org.snapscript.core.error;

import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;

public class RuntimeErrorFormatter {
   
   private final TypeExtractor extractor;
   
   public RuntimeErrorFormatter(TypeExtractor extractor) {
      this.extractor = extractor;
   }
   
   public String formatReferenceError(String name) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Variable '");
      builder.append(name);
      builder.append("' not found in scope");
      
      return builder.toString();
   }
   
   public String formatReferenceError(Object object, String name) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Property '");
      builder.append(name);
      builder.append("' not found");
      
      if(object != null) {
         Type type = extractor.getType(object);
         
         builder.append(" for '");
         builder.append(type);
         builder.append("'");
      }
      return builder.toString();
   }
   
   public String formatInvokeError(String name, Object[] list) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Function '");
      builder.append(name);
      
      String signature = formatSignature(list);
      
      builder.append(signature);
      builder.append("' not found in scope");
      
      return builder.toString();
   }
   
   public String formatInvokeError(Object object, String name, Object[] list) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Function '");
      builder.append(name);
      
      String signature = formatSignature(list);
      
      builder.append(signature);
      builder.append("' not found");
      
      if(object != null) {
         Type type = extractor.getType(object);
         
         builder.append(" for '");
         builder.append(type);
         builder.append("'");
      }
      return builder.toString();
   }
   
   public String formatInvokeError(Type type, String name, Object[] list) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Function '");
      builder.append(name);
      
      String signature = formatSignature(list);
      
      builder.append(signature);
      builder.append("' not found for '");
      builder.append(type);
      builder.append("'");
      
      return builder.toString();
   }
   
   private String formatSignature(Object[] list) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("(");

      for(int i = 0; i < list.length; i++) {
         Object value = list[i];
         Type parameter = extractor.getType(value);
         
         if(i > 0) {
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