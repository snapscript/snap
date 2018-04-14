package org.snapscript.core.error;

public enum Reason {
   ACCESS,
   REFERENCE,
   INVOKE,
   CAST,
   THROW;
   
   public boolean isAccess() {
      return this == ACCESS;
   }
   
   public boolean isReference() {
      return this == REFERENCE;
   }
   
   public boolean isInvoke() {
      return this == INVOKE;
   }   
   
   public boolean isCast() {
      return this == CAST;
   }
   
   public boolean isThrow() {
      return this == THROW;
   }
}
