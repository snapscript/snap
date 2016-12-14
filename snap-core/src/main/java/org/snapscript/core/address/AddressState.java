package org.snapscript.core.address;

import java.util.Iterator;

import org.snapscript.core.Value;

public class AddressState implements State2 {

   protected AddressTable table;
   
   public AddressState(AddressTable table) {
      this.table = table;
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
   
   public void add(String name, Value value){
      table.add(name, value);
   }
   
   public void clear() {
      table.reset(0);
   }
}
