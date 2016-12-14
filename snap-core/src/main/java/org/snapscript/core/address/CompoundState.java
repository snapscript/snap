package org.snapscript.core.address;

import org.snapscript.core.Value;

public class CompoundState implements State2 {

   private final AddressTable table;
   private final State2 inner;
   
   public CompoundState(State2 inner) { // this can wrap multiple types
      this.table = new AddressTable(this);
      this.inner = inner;
   }
   
   public Address address(String name){
      Address address = table.address(name);
      int index = address.getIndex();
      
      if(index < 0) {
         return inner.address(name);
      }
      return address;
   }
   
   public Value get(Address address){
      Object source = address.getSource();
      
      if(source == this) {
         return (Value)table.get(address);
      }
      return inner.get(address);
   }
   
   public void set(Address address, Value value){
      Object source = address.getSource();
      
      if(source == this) { // if its not this
         table.set(address, value);
      } else {
         inner.set(address, value);
      }
   }
   
   public void add(String name, Value value){ // this is called by the DeclareProperty
      table.add(name, value);
   }
}
