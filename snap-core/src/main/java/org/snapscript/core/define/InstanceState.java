package org.snapscript.core.define;

import static org.snapscript.core.StateType.STACK;

import java.util.Iterator;

import org.snapscript.core.Address;
import org.snapscript.core.Bug;
import org.snapscript.core.CompoundIterator;
import org.snapscript.core.CompoundState;
import org.snapscript.core.Stack;
import org.snapscript.core.State;
import org.snapscript.core.Value;

public class InstanceState implements State {
   
   private final Instance base;
   private final State self;
   private final int key;
   
   public InstanceState(Instance base, Stack stack, int key) {
      this.self = new CompoundState(stack, key);
      this.base = base;
      this.key = key;
   }  
   
   @Override
   public Iterator<String> iterator() {
      State object = base.getState();
      Iterator<String> first = self.iterator();
      Iterator<String> second = object.iterator();
      
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
      State object = base.getState();
      Address address = self.address(name);
      int index = address.getIndex();
      
      if(index < 0) {
         return object.address(name);
      }
      return address; 
   }
   
   @Override
   public Value get(String name){
      State object = base.getState();
      Value value = self.get(name);
      
      if(value == null) {
         return object.get(name);
      } 
      return value;
   }
   
   @Override
   public Value get(Address address){
      State object = base.getState();
      int source = address.getSource();
      
      if(source == STACK.mask) {
         return self.get(address);
      }
      if(source == key) {
         return self.get(address);
      }
      return object.get(address); 
   }
   
   @Bug("is something so explicit as 'source == STACK.mask' the right thing to do!!")
   @Override
   public void set(Address address, Value value){
      State object = base.getState();
      int source = address.getSource();

      if(source == STACK.mask) {
         self.set(address, value);
      } else if(source == key) { // if its not this
         self.set(address, value);
      } else {
         object.set(address, value);
      }
   }
   
   @Override
   public Address add(String name, Value value){ // this is called by the DeclareProperty
      return self.add(name, value);
   }
}
