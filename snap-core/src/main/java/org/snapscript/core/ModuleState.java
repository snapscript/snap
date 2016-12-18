package org.snapscript.core;

import java.util.Iterator;

public class ModuleState implements State {
   
   private final Module module;
   private final State stack;
   private final State state;
   
   public ModuleState(Module module, State stack) { // this can wrap multiple types
      this.state = new AddressState(module);
      this.module = module;
      this.stack = stack;
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
      Object source = address.getSource();
      
      if(source == module) {
         return state.get(address);
      } 
      return stack.get(address);
   }
   
   @Override
   public void set(Address address, Value value){
      Object source = address.getSource();
      
      if(source == module) { // if its not this
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
