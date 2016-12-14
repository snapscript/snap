package org.snapscript.core.address;

import org.snapscript.core.Value;

public class ScopeState implements State2 {
   
   private final AddressTable table;
   private final Scope2 scope;
   
   public ScopeState(Scope2 scope) { // this can wrap multiple types
      this.table = new AddressTable(this);
      this.scope = scope;
   }
   
   public Address address(String name){
      State2 inner = scope.getState();
      Address address = inner.address(name);
      int index = address.getIndex();
      
      if(index < 0) {
         return table.address(name);
      }
      return address;
      
   }
   
   public Value get(Address address){
      State2 inner = scope.getState();
      Object source = address.getSource();
      
      if(source == this) {
         return (Value)table.get(address);
      } 
      return inner.get(address);
   }
   
   public void set(Address address, Value value){
      State2 inner = scope.getState();
      Object source = address.getSource();
      
      if(source == this) { // if its not this
         table.set(address, value);
      } else {
         inner.set(address, value);
      }
   }
   
   public void add(String name, Value value){ // this is called by the DeclareProperty
      table.add(name, value);
   }
}
