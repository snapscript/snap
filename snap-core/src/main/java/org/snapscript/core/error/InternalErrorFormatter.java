package org.snapscript.core.error;

import org.snapscript.core.Path;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.trace.Trace;

public class InternalErrorFormatter {
   
   private final TypeExtractor extractor;
   
   public InternalErrorFormatter(TypeExtractor extractor) {
      this.extractor = extractor;
   }

   public String format(Throwable cause, Trace trace) {
      StringBuilder builder = new StringBuilder();
      
      if(trace != null) {
         String message = cause.getMessage();
         Path path = trace.getPath();
         int line = trace.getLine();
         
         builder.append(message);
         builder.append(" in ");
         builder.append(path);
         builder.append(" at line ");
         builder.append(line);

         return builder.toString();
      }
      return cause.getMessage();
   }
   
   public String format(String name, Object... list) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Method '");
      builder.append(name);
      
      String signature = format(list);
      
      builder.append(signature);
      builder.append("' not found in scope");
      
      return builder.toString();
   }
   
   public String format(Object object, String name, Object... list) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Method '");
      builder.append(name);
      
      String signature = format(list);
      
      builder.append(signature);
      builder.append("' not found");
      
      if(object != null) {
         Type type = extractor.getType(object);
         Type entry = type.getEntry();
         
         builder.append(" for '");
         
         if(entry == null) {
            builder.append(type);
         } else {
            builder.append(type);
            builder.append("[]");
         }
         builder.append("'");
      }
      return builder.toString();
   }
   
   public String format(Type type, String name, Object... list) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Method '");
      builder.append(name);
      
      String signature = format(list);
      
      builder.append(signature);
      builder.append("' not found for '");
      
      Type entry = type.getEntry();
         
      if(entry == null) {
         builder.append(type);
      } else {
         builder.append(type);
         builder.append("[]");
      }
      builder.append("'");
      
      return builder.toString();
   }
   
   public String format(Type type, String name, Type... list) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Method '");
      builder.append(name);
      
      String signature = format(list);
      
      builder.append(signature);
      builder.append("' not found for '");
      
      Type entry = type.getEntry();
         
      if(entry == null) {
         builder.append(type);
      } else {
         builder.append(type);
         builder.append("[]");
      }
      builder.append("'");
      
      return builder.toString();
   }
   
   private String format(Object... list) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("(");

      for(int i = 0; i < list.length; i++) {
         Object value = list[i];
         Type parameter = extractor.getType(value);
         
         if(i > 0) {
            builder.append(", ");
         }
         builder.append(parameter);
      }
      builder.append(")");
      
      return builder.toString();
   }
   
   private String format(Type... list) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("(");

      for(int i = 0; i < list.length; i++) {
         Type type = list[i];

         if(i > 0) {
            builder.append(", ");
         }
         builder.append(type);
      }
      builder.append(")");
      
      return builder.toString();
   }
}