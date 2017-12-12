package org.snapscript.core.error;

public class InternalError extends Error {
   
   private final Object original;
   
   public InternalError(Object original) {
      this.original = original;
   }

   public Object getValue() {
      return original;
   }
   
   @Override
   public Throwable getCause() {
      if(Throwable.class.isInstance(original)) {
         return (Throwable)original;
      }
      return null;
   }
   
   @Override
   public String toString() {
      return String.valueOf(original);
   }
}