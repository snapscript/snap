package org.snapscript.core.address;

import org.snapscript.core.Value;

public class ScopeState implements State2 {
   
   private final AddressTable table;
   private final Scope2 scope;
   
   public ScopeState(Scope2 scope) { // this can wrap multiple types
      this.table = new AddressTable();
      this.scope = scope;
   }
   
   public Address address(String name){
      State2 inner = scope.getState();
      int index = table.index(name);
      
      if(index >= 0) {
         return new Address(name, this, index);
      }
      return inner.address(name);
   }
   
   public Value get(Address address){
      State2 inner = scope.getState();
      Object source = address.getSource();
      int index = address.getIndex();
      
      if(source == this) {
         return (Value)table.get(index);
      } 
      return inner.get(address);
   }
   
   public void set(Address address, Value value){
      State2 inner = scope.getState();
      Object source = address.getSource();
      int index = address.getIndex();
      
      if(source == this) { // if its not this
         table.set(index, value);
      } else {
         inner.set(address, value);
      }
   }
   
   public void add(String name, Value value){ // this is called by the DeclareProperty
      table.add(name, value);
   }
}
