package org.snapscript.core.stack;

public class Address {

   private final String name;
   private final int scope;
   private final int index;
   
   public Address(String name, int scope, int index) {
      this.scope = scope;
      this.name = name;
      this.index = index;
   }
   
   public int getScope(){
      return scope;
   }
   
   public int getIndex(){
      return index;
   }
   
   public String getName(){
      return name;
   }
}
