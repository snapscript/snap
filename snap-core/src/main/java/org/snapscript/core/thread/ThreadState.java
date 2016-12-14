package org.snapscript.core.thread;

import org.snapscript.common.LongStack;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Value;
import org.snapscript.core.address.Address;
import org.snapscript.core.address.AddressTable;
import org.snapscript.core.address.State2;

// this is an implementation of State2, however it has some
// additional elements that allow the stack to be peeled
public class ThreadState implements State2 {

   private final AddressTable table; // even=name, odd=value
   private final LongStack stack;
   
   public ThreadState() {
      this(8192);
   }
   
   public ThreadState(int size) {
      this.table = new AddressTable(size);
      this.stack = new LongStack(size);
   }
   
   public void mark(boolean visible) {
      long position = table.position();
   
      if(!visible) {
         table.mark();
      } 
      stack.push(position);
   }
   
   public void reset() {
      long position = stack.pop();
      
      if(position < 0) {
         throw new InternalStateException("Invalid stack reset");
      }
      table.reset(position);
   }
   
   public Address address(String name){
      int index = table.index(name);
      
      if(index >= 0) {
         return new Address(name, null, index);
      }
      return new Address(name, null, -1);
   }
   
   public Value get(Address address){
      Object type = address.getSource();
      String name = address.getName();
      int index = address.getIndex();
      
      if(type != null || index < 0) {
         throw new InternalStateException("Invalid address access for '" + name +"'");
      }
      return (Value)table.get(index);
   }
   
   public void set(Address address, Value value){
      Object type = address.getSource();
      String name = address.getName();
      int index = address.getIndex();
      
      if(type != null || index < 0) {
         throw new InternalStateException("Invalid address access for '" + name +"'");
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
