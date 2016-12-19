package org.snapscript.core;

import java.util.Iterator;

public class AddressState implements Stack {

   protected AddressTable table;
   
   public AddressState(int key) {
      this.table = new AddressTable(key);
   }
   
   @Override
   public Iterator<String> iterator(){
      return table.iterator();
   }
   
   @Override
   public Address address(String name){
      return table.address(name);
   }
   
   @Override
   public Value get(Address address){
      return (Value)table.get(address);
   }
   
   @Override
   public Value get(String name){
      return (Value)table.get(name);
   }
   
   @Override
   public void set(Address address, Value value){
      table.set(address, value);
   }
   
   @Override
   public Address add(String name, Value value){
      return table.add(name, value);
   }
   
   @Override
   public boolean contains(String name){
      return table.contains(name);
   }
   
   public void clear() {
      table.reset(0);
   }
}
