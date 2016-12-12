package org.snapscript.core.stack;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Value;

// only
public class StackState implements State2 {

   private AddressTable table; // even=name, odd=value
   private int depth;
   
   public StackState(AddressTable table, int depth) {
      this.table = table;
      this.depth = depth;
   }
   
   public State2 create() {
      int size = table.size();
      
      if(size > depth) {
         return new StackState(table,size);
      }
      return this; // no variables on stack
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
