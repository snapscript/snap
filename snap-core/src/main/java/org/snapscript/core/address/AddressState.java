package org.snapscript.core.address;

import org.snapscript.core.Value;

// only
public class AddressState implements State2 {

   protected AddressTable table; // even=name, odd=value
   protected long depth;
   
   public AddressState(AddressTable table, long depth) {
      this.table = table;
      this.depth = depth;
   }
   
   public Address address(String name){
      return table.address(name);
   }
   
   public Value get(Address address){
      return (Value)table.get(address);
   }
   
   public void set(Address address, Value value){
      table.set(address, value);
   }
   
   public void add(String name, Value value){
      table.add(name, value);
   }
   
   public void clear() {
      table.reset(depth);
   }
}
