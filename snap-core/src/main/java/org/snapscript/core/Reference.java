package org.snapscript.core;

public class Reference extends Value {
   
   private Object value;
   private Type type;
   private boolean property;
   
   public Reference(Object value) {
      this(value, null);
   }
   
   public Reference(Object value, Type type) {
      this(value, type, false);
   }
   
   public Reference(Object value, Type type, boolean property) {
      this.property = property;
      this.value = value;
      this.type = type;
   }
   
   @Override
   public boolean isProperty(){
      return property;
   }
   
   @Override
   public Type getConstraint() {
      return type;
   }
   
   @Override
   public <T> T getValue() {
      return (T)value;
   }

   @Override
   public void setValue(Object value) {
      this.value = value;
   }
   
   @Override
   public String toString() {
      return String.valueOf(value);
   }
}