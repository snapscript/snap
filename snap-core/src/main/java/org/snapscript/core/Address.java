package org.snapscript.core;

public class Address {

   private final Object source;
   private final String name;
   private final int index;
   
   public Address(String name, Object source, int index) {
      this.source = source;
      this.name = name;
      this.index = index;
   }
   
   public int getIndex(){
      return index;
   }
   
   public Object getSource(){
      return source;
   }
   
   public String getName(){
      return name;
   }
}
