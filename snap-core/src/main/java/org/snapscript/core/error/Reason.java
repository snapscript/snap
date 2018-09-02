package org.snapscript.core.error;

public enum Reason {
   ACCESS,
   REFERENCE,
   INVOKE,
   CAST,
   GENERIC,
   CONSTRUCTION,
   THROW;
   
   public boolean isAccess() {
      return this == ACCESS;
   }

   public boolean isGeneric() {
      return this == GENERIC;
   }

   public boolean isConstruction() {
      return this == CONSTRUCTION;
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
