package org.snapscript.core.variable;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class DataMapper {
   
   public static Data toData(Value value) {
      return new ValueData(value);
   }

   private static class ValueData implements Data {
      
      private final Value value;
      
      public ValueData(Value value) {
         this.value = value;
      }

      @Override
      public Type getType(Scope scope) {
         Object object = value.getValue();
         
         if(object != null) {
            return scope.getModule().getContext().getExtractor().getType(object);
         }
         return null;
      }

      @Override
      public <T> T getValue() {
         return value.getValue();
      }
   }
}
