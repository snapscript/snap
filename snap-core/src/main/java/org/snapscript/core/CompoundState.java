package org.snapscript.core;

import java.util.Iterator;

/*

We need to give a miss a special value of ?????

We need to give the stack a special value of '-1'
We need to give the model a special value of '-2'
We need to give the module a special value of '-3'

{
   object {
      state {
         stack
         table
      }
      base {
         state {
            stack
            table
         }
         base {
            state {
               stack
               table
            }
         }
      }
   }
}
*/
public class CompoundState implements State {

   private final State state;
   private final Stack stack;
   private final int key;
   
   @Bug("This key does not repreent the stack!!!  how do we know to dive inside??????")
   public CompoundState(Stack stack, int key) { // this can wrap multiple types
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
      if(!state.contains(name)) {
         return stack.contains(name);
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
      Address address = state.address(name);
      int index = address.getIndex();
      
      if(index < 0) {
         return stack.get(name);
      }
      return state.get(name);
   }
   
   @Override
   public Value get(Address address){
      int source = address.getSource();
      
      if(source != key) {
         return stack.get(address);
      }
      return state.get(address);
   }
   
   @Override
   public void set(Address address, Value value){
      int source = address.getSource();
      
      if(source != key) { // if its not this
         stack.set(address, value);
      } else {
         state.set(address, value);
      }
   }
   
   @Override
   public Address add(String name, Value value){ 
      return state.add(name, value);
   }
}
