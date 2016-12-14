package org.snapscript.core.thread;

import org.snapscript.common.IntegerStack;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Value;
import org.snapscript.core.address.Address;
import org.snapscript.core.address.AddressTable;
import org.snapscript.core.address.State2;

// this is an implementation of State2, however it has some
// additional elements that allow the stack to be peeled
public class ThreadState implements State2 {

   private final IntegerStack stack;
   private final AddressTable table; // even=name, odd=value
   
   public ThreadState() {
      this.stack = new IntegerStack();
      this.table = new AddressTable();
   }
   
   public int push(boolean visible) {
      int size = table.size();
      
      stack.push(size);
      return size;
   }
   
   public void pop(boolean visible) {
      int size = stack.pop();
      table.reset(size);
   }
   
   public Address address(String name){
      int index = table.indexOf(name);
      
      if(index >= 0) {
         return new Address(name, 0, index);
      }
      return null;
   }
   
   public Value get(Address address){
      Object type = address.getSource();
      int index = address.getIndex();
      
      if(type != null) {
         throw new InternalStateException("Invalid address access " + type);
      }
      return (Value)table.get(index);
   }
   
   public void set(Address address, Value value){
      Object type = address.getSource();
      int index = address.getIndex();
      
      if(type != null) {
         throw new InternalStateException("Invalid address access " + type);
      }
      table.set(index, value);
   }
   
   public void add(String name, Value value){
      table.add(name, value);
   }
   
   public void clear() {
      table.reset(0);
   }
}
