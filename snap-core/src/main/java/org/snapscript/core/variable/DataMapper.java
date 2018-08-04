package org.snapscript.core.variable;

import org.snapscript.core.type.Type;

public class DataMapper {
   
   public static Data toData(Value value) {
      return new ValueData(value);
   }

   private static class ValueData implements Data {
      
      private final Value value;
      private Type type;
      
      public ValueData(Value value) {
         this.value = value;
      }

      @Override
      public Type getType() {
         if(type == null) {
            Object object = value.getValue();
            
            if(object != null) {
               type = value.getSource().getScope().getModule().getContext().getExtractor().getType(object);
            }
         }
         return type;
      }

      @Override
      public <T> T getValue() {
         return value.getValue();
      }
   }
}
