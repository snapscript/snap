package org.snapscript.core.function;

import java.util.List;

import org.snapscript.core.Type;

public class SignatureDescription {

   private final Signature signature;
   private final int start;
   
   public SignatureDescription(Signature signature) {
      this(signature, 0);
   }
  
   public SignatureDescription(Signature signature, int start) {
      this.signature = signature;
      this.start = start;
   }

   public String getDescription() {
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
            
            if(parameter.isVariable()) {
               builder.append("...");
            }
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
      return getDescription();
   }
}