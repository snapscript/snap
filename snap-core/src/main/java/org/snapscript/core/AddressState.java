package org.snapscript.core;

import java.util.Iterator;

public class AddressState implements State {

   protected AddressTable table;
   
   public AddressState(Object key) {
      this.table = new AddressTable(key);
   }
   
   public Iterator<String> iterator(){
      return table.iterator();
   }
   
   public Address address(String name){
      return table.address(name);
   }
   
   public Value get(Address address){
      return (Value)table.get(address);
   }
   
   public Value get(String name){
      return (Value)table.get(name);
   }
   
   public void set(Address address, Value value){
      table.set(address, value);
   }
   
   public Address add(String name, Value value){
      return table.add(name, value);
   }
   
   public boolean contains(String name){
      return table.contains(name);
   }
   
   public void clear() {
      table.reset(0);
   }
}
