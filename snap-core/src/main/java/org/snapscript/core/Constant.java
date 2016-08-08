package org.snapscript.core;

public class Constant extends Value {
   
   private final Object value;
   private final Type type;
   
   public Constant(Object value) {
      this(value, null);
   }

   public Constant(Object value, Type type) {
      this.value = value;
      this.type = type;
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
   public void setValue(Object value){
      throw new InternalStateException("Illegal modification of constant");
   } 
   
   @Override
   public String toString() {
      return String.valueOf(value);
   }
}
