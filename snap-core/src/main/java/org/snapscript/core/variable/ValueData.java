package org.snapscript.core.variable;

import org.snapscript.core.Entity;
import org.snapscript.core.type.Type;

public class ValueData implements Data {
   
   private final Object value;
   private Entity source;
   private Type type;
   
   public ValueData(Object value, Entity source) {
      this.source = source;
      this.value = value;
   }

   @Override
   public Type getType() {
      if(type == null && value != null) {            
         type = source.getScope().getModule().getContext().getExtractor().getType(value);
         
      }
      return type;
   }   

   @Override
   public double getDouble() {
      Number number = getNumber();

      if (number != null) {
         return number.doubleValue();
      }
      return 0;
   }

   @Override
   public long getLong() {
      Number number = getNumber();

      if (number != null) {
         return number.longValue();
      }
      return 0;
   }

   @Override
   public int getInteger() {
      Number number = getNumber();

      if (number != null) {
         return number.intValue();
      }
      return 0;
   }

   @Override
   public Number getNumber() {
      Object value = getValue();

      if (value != null) {
         return ValueMapper.toNumber(value); 
      }
      return null;
   }
   
   @Override
   public char getCharacter() {
      Object value = getValue();

      if (value != null) {
         return ValueMapper.toCharacter(value); 
      }
      return 0;
   }
   
   @Override
   public String getString() {
      Object value = getValue();

      if (value != null) {
         return value.toString();
      }
      return null;
   }  

   @Override
   public <T> T getValue() {
      return (T)value;
   }
   
   @Override
   public String toString() {
      return String.valueOf(value);
   }
}
