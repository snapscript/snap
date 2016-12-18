package org.snapscript.core;

import java.util.Iterator;

public class ScopeState implements State {
   
   private final State global;
   private final Scope scope;
   private final Object key;
   
   public ScopeState(Scope scope, State global, Object key) { // this can wrap multiple types
      this.global = global;
      this.scope = scope;
      this.key = key;
   }
   
   @Override
   public Iterator<String> iterator() {
      State local = scope.getState();
      Iterator<String> first = global.iterator();
      Iterator<String> second = local.iterator();
      
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
      State local = scope.getState();
      Address address = local.address(name);
      int index = address.getIndex();
      
      if(index < 0) {
         return global.address(name);
      }
      return address; 
   }
   
   @Override
   public Value get(String name){
      State local = scope.getState();
      Address address = local.address(name);
      int index = address.getIndex();
      
      if(index < 0) {
         return global.get(address);
      } 
      return local.get(name);
   }
   
   @Override
   public Value get(Address address){
      State local = scope.getState();
      Object source = address.getSource();
      
      if(source == key) {
         return global.get(address);
      } 
      return local.get(address);
   }
   
   @Override
   public void set(Address address, Value value){
      State local = scope.getState();
      Object source = address.getSource();
      
      if(source == key) { // if its not this
         global.set(address, value);
      } else {
         local.set(address, value);
      }
   }
   
   @Override
   public Address add(String name, Value value){ // this is called by the DeclareProperty
      State local = scope.getState();
      return local.add(name, value);
   }
}
