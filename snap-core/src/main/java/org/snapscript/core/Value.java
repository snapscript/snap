package org.snapscript.core;

public abstract class Value {
   
   public Boolean getBoolean() {
      Object value = getValue();

      if (value != null) {
         return (Boolean) value;// optimistic!!
      }
      return null;
   }

   public Number getNumber() {
      Object value = getValue();

      if (value != null) {
         return (Number) value; // optimistic!!
      }
      return null;
   }

   public Double getDouble() {
      Number number = getNumber();

      if (number != null) {
         return number.doubleValue();
      }
      return null;
   }

   public Long getLong() {
      Number number = getNumber();

      if (number != null) {
         return number.longValue();
      }
      return null;
   }

   public Integer getInteger() {
      Number number = getNumber();

      if (number != null) {
         return number.intValue();
      }
      return null;
   }

   public Float getFloat() {
      Number number = getNumber();

      if (number != null) {
         return number.floatValue();
      }
      return null;
   }

   public String getString() {
      Object value = getValue();

      if (value != null) {
         return value.toString();
      }
      return null;
   }   
   
   public Class getType() {
      Object value = getValue();
      
      if(value != null) {
         return value.getClass();         
      }
      return null;
   }     
   
   public Type getConstraint(){
      return null; 
   }   
   
   public boolean isProperty() {
      return false;
   }

   public abstract <T> T getValue();
   public abstract void setValue(Object value);
}
