package org.snapscript.core.address;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Value;

// only
public class AddressState implements State2 {

   protected AddressTable table; // even=name, odd=value
   protected int depth;
   
   public AddressState(AddressTable table, int depth) {
      this.table = table;
      this.depth = depth;
   }
   
   public Address address(String name){
      int index = table.indexOf(name);
      
      if(index >= 0) {
         return new Address(name, 0, index);
      }
      return null;
   }
   
   public Value get(Address address){
      int index = address.getIndex();
      
      if(index < depth) {
         throw new InternalStateException("Address '" + index + "' is out of scope");
      }
      return (Value)table.get(index);
   }
   
   public void set(Address address, Value value){
      int index = address.getIndex();

      if(index < depth) {
         throw new InternalStateException("Address '" + index + "' is out of scope");
      }
      table.set(index, value);
   }
   
   public void add(String name, Value value){
      table.add(name, value);
   }
   
   public void clear() {
      table.reset(depth);
   }
}
