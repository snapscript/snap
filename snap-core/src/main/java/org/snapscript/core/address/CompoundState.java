package org.snapscript.core.address;

import java.util.Iterator;

import org.snapscript.core.Value;

public class CompoundState implements State2 {

   private final AddressTable table;
   private final State2 inner;
   
   public CompoundState(State2 inner) { // this can wrap multiple types
      this.table = new AddressTable(this);
      this.inner = inner;
   }
   
   @Override
   public Iterator<String> iterator() {
      Iterator<String> first = table.iterator();
      Iterator<String> second = inner.iterator();
      
      return new CompoundIterator<String>(first, second);
   }
   
   @Override
   public Address address(String name){
      Address address = table.address(name);
      int index = address.getIndex();
      
      if(index < 0) {
         return inner.address(name);
      }
      return address;
   }
   
   @Override
   public Value get(String name){
      Address address = table.address(name);
      int index = address.getIndex();
      
      if(index < 0) {
         return inner.get(name);
      }
      return (Value)table.get(name);
   }
   
   @Override
   public Value get(Address address){
      Object source = address.getSource();
      
      if(source == this) {
         return (Value)table.get(address);
      }
      return inner.get(address);
   }
   
   @Override
   public void set(Address address, Value value){
      Object source = address.getSource();
      
      if(source == this) { // if its not this
         table.set(address, value);
      } else {
         inner.set(address, value);
      }
   }
   
   @Override
   public void add(String name, Value value){ 
      table.add(name, value);
   }
}
