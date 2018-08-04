package org.snapscript.core.variable;

import org.snapscript.core.type.Type;

public abstract class DataValue extends Value implements Data {   

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
   public Type getType() {
      Object value = getValue();

      if (value != null) {
         return getSource().getScope().getModule().getContext().getExtractor().getType(value);
      }
      return null;
   }  

}
