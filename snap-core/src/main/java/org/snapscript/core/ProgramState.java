package org.snapscript.core;

import java.util.Iterator;

public class ProgramState implements State {

   private final State global;
   private final State state;
   
   public ProgramState(Scope scope, Model model, int key) { 
      this.global = new ModelState(model);
      this.state = new ScopeState(scope, global, key);
   }
   
   @Override
   public Iterator<String> iterator() {
      return state.iterator();
   }
   
   @Override
   public boolean contains(String name) {
      return state.contains(name);
   }
   
   @Override
   public Address address(String name){
      return state.address(name);
   }
   
   @Override
   public Value get(String name){
      return state.get(name);
   }
   
   @Override
   public Value get(Address address){
      return state.get(address);
   }
   
   @Override
   public void set(Address address, Value value){
      state.set(address, value);
   }
   
   @Override
   public Address add(String name, Value value){ 
      return state.add(name, value);
   }
}
