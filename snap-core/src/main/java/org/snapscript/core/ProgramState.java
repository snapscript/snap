package org.snapscript.core;

import java.util.Iterator;

public class ProgramState implements State {

   private final State global;
   private final State local;
   private final Model model;
   
   public ProgramState(Scope scope, Model model) { // this can wrap multiple types
      this.global = new ModelState(model);
      this.local = new ScopeState(scope, global, model);
      this.model = model;
   }
   
   @Override
   public Iterator<String> iterator() {
      Iterator<String> first = global.iterator();
      Iterator<String> second = local.iterator();
      
      return new CompoundIterator<String>(first, second);
   }
   
   @Override
   public boolean contains(String name) {
      if(!local.contains(name)) {
         return global.contains(name);
      }
      return true;
   }
   
   @Override
   public Address address(String name){
      Address address = local.address(name);
      int index = address.getIndex();
      
      if(index < 0) {
         return global.address(name);
      }
      return address;
   }
   
   @Override
   public Value get(String name){
      Address address = local.address(name);
      int index = address.getIndex();
      
      if(index < 0) {
         return global.get(name);
      }
      return local.get(name);
   }
   
   @Override
   public Value get(Address address){
      Object source = address.getSource();
      
      if(source == model) {
         return global.get(address);
      }
      return local.get(address);
   }
   
   @Override
   public void set(Address address, Value value){
      Object source = address.getSource();
      
      if(source == model) { // if its not this
         global.set(address, value);
      } else {
         local.set(address, value);
      }
   }
   
   @Override
   public Address add(String name, Value value){ 
      return local.add(name, value);
   }
}
