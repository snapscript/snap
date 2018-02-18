package org.snapscript.core;

public abstract class Constraint {
   
   public boolean isInstance() {
      return true;
   }
   
   public boolean isStatic() {
      return false;
   }
   
   public abstract Type getType(Scope scope);
}