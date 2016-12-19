package org.snapscript.core;

public class Address {

   private final String name;
   private final int source;
   private final int index;
   
   public Address(String name, int source, int index) {
      this.source = source;
      this.name = name;
      this.index = index;
   }
   
   public int getIndex(){
      return index;
   }
   
   public int getSource(){
      return source;
   }
   
   public String getName(){
      return name;
   }
}
