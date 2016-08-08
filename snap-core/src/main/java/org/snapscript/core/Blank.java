package org.snapscript.core;

import java.util.concurrent.atomic.AtomicReference;

public class Blank extends Value {
   
   private final AtomicReference<Object> reference;
   private final Type type;
   
   public Blank() {
      this(null);
   }

   public Blank(Type type) {
      this.reference = new AtomicReference<Object>();
      this.type = type;
   }
   
   @Override
   public Type getConstraint() {
      return type;
   }
   
   @Override
   public <T> T getValue() {
      return (T)reference.get();
   }
   
   @Override
   public void setValue(Object value){
      if(!reference.compareAndSet(null, value)) {
         throw new InternalStateException("Illegal modification of constant");
      }
   } 
   
   @Override
   public String toString() {
      return String.valueOf(reference);
   }
}
