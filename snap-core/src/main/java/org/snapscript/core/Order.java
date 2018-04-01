package org.snapscript.core;

public enum Order {
   STATIC, // static declarations
   INSTANCE, // instance declarations
   OTHER; // other
   
   public boolean isStatic(){
      return this == STATIC;
   }
   
   public boolean isInstance(){
      return this == INSTANCE;
   }
}
