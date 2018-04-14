package org.snapscript.core.error;

import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import org.snapscript.core.type.Type;

public class CompileErrorFormatter {
   
   public CompileErrorFormatter() {
      super();
   }

   public String formatAccessError(String name) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Variable '");
      builder.append(name);
      builder.append("' is not accessible in scope");
      
      return builder.toString();
   }
   
   public String formatAccessError(Type type, String name) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Property '");
      builder.append(name);
      builder.append("' for '");
      builder.append(type);
      builder.append("' is not accessible");
      
      return builder.toString();
   }
   
   public String formatReferenceError(String name) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Variable '");
      builder.append(name);
      builder.append("' not found in scope");
      
      return builder.toString();
   }
   
   public String formatReferenceError(Type type, String name) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Property '");
      builder.append(name);
      builder.append("' not found for '");
      builder.append(type);
      builder.append("'");
      
      return builder.toString();
   }
   
   public String formatAccessError(String name, Type[] list) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Function '");
      builder.append(name);
      
      String signature = formatSignature(list);
      
      builder.append(signature);
      builder.append("' is not accessible");
      
      return builder.toString();
   }
   
   public String formatAccessError(Type type, String name, Type[] list) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Function '");
      builder.append(name);
      
      String signature = formatSignature(list);
      
      builder.append(signature);
      builder.append("' for '");
      builder.append(type);
      builder.append("' is not accessible");
      
      return builder.toString();
   }
   
   public String formatInvokeError(String name, Type[] list) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Function '");
      builder.append(name);
      
      String signature = formatSignature(list);
      
      builder.append(signature);
      builder.append("' not found in scope");
      
      return builder.toString();
   }
   
   public String formatInvokeError(Type type, String name, Type[] list) {
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
   
   public String formatCastError(Type require, Type actual) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Cast from '");
      builder.append(actual);
      builder.append("' to '");
      builder.append(require);
      builder.append("' is not possible");
      
      return builder.toString();
   }

   private String formatSignature(Type[] list) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("(");

      for(int i = 0; i < list.length; i++) {
         Type type = list[i];

         if(i > 0) {
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
}