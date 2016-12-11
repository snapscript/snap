package org.snapscript.core;

public class Reference extends Value {
   
   private Object value;
   private Type type;
   private int modifiers;
   
   public Reference(Object value) {
      this(value, null);
   }
   
   public Reference(Object value, Type type) {
      this(value, type, -1);
   }
   
   public Reference(Object value, Type type, int modifiers) {
      this.modifiers = modifiers;
      this.value = value;
      this.type = type; 
   }
   
   @Override
   public boolean isProperty(){
      return modifiers != -1;
   }
   
   @Override
   public int getModifiers(){
      return modifiers;
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