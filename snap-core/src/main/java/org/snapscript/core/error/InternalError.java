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
   public String toString() {
      return String.valueOf(original);
   }
}
