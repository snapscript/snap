package org.snapscript.core;

public class Transient extends Value {
   
   private final Object object;
   private final Type type;
   
   public Transient(Object object) {
      this(object, null);
   }
   
   public Transient(Object object, Type type) {
      this.object = object;
      this.type = type;
   }
   
   @Override
   public Type getConstraint(){
      return type;
   }
   
   @Override
   public <T> T getValue(){
      return (T)object;
   }
   
   @Override
   public void setValue(Object value){
      throw new InternalStateException("Illegal modification of transient");
   } 
   
   @Override
   public String toString() {
      return String.valueOf(object);
   }
}
