package org.snapscript.core.function;

import java.util.List;

import org.snapscript.core.Module;
import org.snapscript.core.Type;

public class FunctionDescription {

   private final Signature signature;
   private final String name;
   private final Type type;
   private final int start;
   
   public FunctionDescription(Signature signature, Type type, String name) {
      this(signature, type, name, 0);
   }
  
   public FunctionDescription(Signature signature, Type type, String name, int start) {
      this.signature = signature;
      this.start = start;
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
         builder.append(name);
         builder.append(".");
      } 
      builder.append(name);
      return builder.toString();
   }
   
   public String getParameters() {
      StringBuilder builder = new StringBuilder();

      builder.append("(");
      
      if(signature != null) {
         List<Parameter> parameters = signature.getParameters();
         int size = parameters.size();
         
         for(int i = start; i < size; i++) {
            Parameter parameter = parameters.get(i);
            Type type = parameter.getType();
            String name = parameter.getName();
            
            if(i > start) {
               builder.append(", ");
            }
            builder.append(name);
            
            if(type != null) {
               String constraint = type.getName();
               
               builder.append(": ");
               builder.append(constraint);
            }
         }
      }
      builder.append(")");
      return builder.toString();
   }
   
   @Override
   public String toString() {
      String description = getDescription();
      String parameters = getParameters();
      
      return description + parameters;
   }
}
