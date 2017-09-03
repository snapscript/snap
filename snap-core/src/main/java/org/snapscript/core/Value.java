package org.snapscript.core;

public abstract class Value {
   
   public static Value getConstant(Object value) {
      return new Constant(value);
   }
   
   public static Value getConstant(Object value, Type type) {
      return new Constant(value, type);
   }
   
   public static Value getReference(Object value) {
      return new Reference(value);
   }
   
   public static Value getReference(Object value, Type type) {
      return new Reference(value, type);
   }
   
   public static Value getProperty(Object value, Type type, int modifiers) {
      return new Reference(value, type, modifiers);
   }
   
   public static Value getBlank(Object value, Type type, int modifiers) {
      return new Blank(value, type, modifiers);
   }
   
   public static Value getTransient(Object value) {
      return new Transient(value);
   }
   
   public static Value getTransient(Object value, Type type) {
      return new Transient(value, type);
   }
   
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
   
   public Character getCharacter() {
      Object value = getValue();

      if (value != null) {
         return (Character) value; // optimistic!!
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
   
   public int getModifiers(){
      return -1;
   }

   public abstract <T> T getValue();
   public abstract void setValue(Object value);
}