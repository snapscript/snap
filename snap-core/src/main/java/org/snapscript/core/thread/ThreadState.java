package org.snapscript.core.thread;

import java.util.Iterator;

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
      this.table = new AddressTable(null, size);
      this.stack = new LongStack(size);
   }
   
   @Override
   public Iterator<String> iterator() {
      return table.iterator();
   }
   
   @Override
   public Address address(String name){
      if(name == null) {
         throw new InternalStateException("Illegal request for null name");
      }
      return table.address(name);
   }
   
   @Override
   public Value get(Address address){
      Object type = address.getSource();
      String name = address.getName();
      
      if(type != null) {
         throw new InternalStateException("Illegal access for '" + name +"'");
      }
      return (Value)table.get(address);
   }
   
   @Override
   public Value get(String name) {
      return (Value)table.get(name);
   }
   
   @Override
   public void set(Address address, Value value){
      Object type = address.getSource();
      String name = address.getName();
      
      if(type != null) {
         throw new InternalStateException("Illegal access for '" + name +"'");
      }
      table.set(address, value);
   }
   
   @Override
   public void add(String name, Value value){
      table.add(name, value);
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
         throw new InternalStateException("Illegal stack reset");
      }
      table.reset(position);
   }
   
   public void clear() {
      table.reset(0);
   }
}
