package org.snapscript.core.stack;

import org.snapscript.core.Value;

// only
public class StackFrame {

   private Object[] stack; // even=name, odd=value
   private State state;
   private int first;
   private int last;
   public StackFrame() {
     
   }
   
   public Value getValue(Address address) {
      int index = address.getIndex();

      if(index == -1) { // this is not on the stack, its in the object
         return state.get(address);
      }
      if(index >= first) {
         return (Value)stack[index];
      }
      return null; // maybe exception
   }
   
   public Address getAddress(String name) {
      for(int i = last; i > first; i+=2) {
         if(stack[i].equals(name)) {
            return new Address(name, i);
         }
      }
      if(state != null) {
         return state.index(name); // the address from the inner state!!
      }
      return new Address(name, last+1);
   }

   private class AddressTable {
      private Object[] table; // growable memory adddress
   }
   
   // this will allow 
   public interface State extends Iterable<String>{
      State create(); // create a new scope in this frame, we cannot access its values
      State create(State inner); // we can extend this frame and access its variables
      Address index(String name);
      Value get(Address name); // gets the value from the frame
      void set(Address name, Value value);
      void add(String name, Value value);
      void clear(); // clear the stack for this
   }
}
