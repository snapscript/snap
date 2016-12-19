package org.snapscript.core;

import java.util.Iterator;

@Bug("This should represent the stack as it is a special stack for the instance....")
public class ModuleState implements Stack {
   
   private final State stack;
   private final State state;
   private final int key;
   
   public ModuleState(State stack, int key) { // this can wrap multiple types
      this.state = new AddressState(key);
      this.stack = stack;
      this.key = key;
   }
   
   @Override
   public Iterator<String> iterator() {
      Iterator<String> first = state.iterator();
      Iterator<String> second = stack.iterator();
      
      return new CompoundIterator<String>(first, second);
   }
   
   @Override
   public boolean contains(String name) {
      Address address = address(name);
      int index = address.getIndex();
  
      if(index < 0) {
         return false;
      }
      return true;
   }
   
   @Override
   public Address address(String name){
      Address address = state.address(name);
      int index = address.getIndex();
      
      if(index < 0) {
         return stack.address(name);
      }
      return address; 
   }
   
   @Override
   public Value get(String name){
      Value value = state.get(name);
      
      if(value == null) {
         return stack.get(name);
      } 
      return state.get(name);
   }
   
   @Override
   public Value get(Address address){
      int source = address.getSource();
      
      if(source == key) {
         return state.get(address);
      } 
      return stack.get(address);
   }
   
   @Override
   public void set(Address address, Value value){
      int source = address.getSource();
      
      if(source == key) { // if its not this
         state.set(address, value);
      } else {
         stack.set(address, value);
      }
   }
   
   @Override
   public Address add(String name, Value value){ 
      return state.add(name, value);
   }
}
