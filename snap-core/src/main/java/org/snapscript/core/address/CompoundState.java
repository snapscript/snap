package org.snapscript.core.address;

import org.snapscript.core.Value;

public class CompoundState implements State2 {

   private final AddressTable table;
   private final State2 inner;
   
   public CompoundState(State2 inner) { // this can wrap multiple types
      this.table = new AddressTable();
      this.inner = inner;
   }
   
   public Address address(String name){
      int index = table.indexOf(name);
      
      if(index >= 0) {
         return new Address(name, 0, index);
      }
      return inner.address(name);
   }
   
   public Value get(Address address){
      Object source = address.getSource();
      int index = address.getIndex();
      
      if(source == this) {
         return (Value)table.get(index);
      }
      return inner.get(address);
   }
   
   public void set(Address address, Value value){
      Object source = address.getSource();
      int index = address.getIndex();
      
      if(source == this) { // if its not this
         table.set(index, value);
      } else {
         inner.set(address, value);
      }
   }
   
   public void add(String name, Value value){ // this is called by the DeclareProperty
      table.add(name, value);
   }
}
