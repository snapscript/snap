package org.snapscript.core.error;

public enum Reason {
   ACCESS,
   REFERENCE,
   INVOKE,
   THROW;
   
   public boolean isAccess() {
      return this == ACCESS;
   }
   
   public boolean isReference() {
      return this == REFERENCE;
   }
}
