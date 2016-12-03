package org.snapscript.core.stack;

public class Address {

   private final String name;
   private final int index;
   
   public Address(String name, int index) {
      this.name = name;
      this.index = index;
   }
   
   public String getName(){
      return name;
   }
   
   public int getIndex(){
      return index;
   }
}
