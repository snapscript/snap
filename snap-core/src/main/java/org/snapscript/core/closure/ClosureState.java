package org.snapscript.core.closure;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.snapscript.core.Address;
import org.snapscript.core.CompoundIterator;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;

public class ClosureState implements State {
   
   private final Map<String, Value> values;
   private final Scope scope;

   public ClosureState(Scope scope) {
      this.values = new ConcurrentHashMap<String, Value>();
      this.scope = scope;
   }
   
   @Override
   public Address address(String name) {
      return new Address(name, null, -1);
   }
   
   @Override
   public boolean contains(String name) {
      Value value = values.get(name);
      
      if(value == null) {
         State state = scope.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         return state.contains(name);
      }
      return true;
   }
   
   @Override
   public Iterator<String> iterator() {
      Set<String> names = values.keySet();
      Iterator<String> iterator =  names.iterator();
      
      if(scope != null) {
         State state = scope.getState();
         Iterator<String> inner = state.iterator();
         
         return new CompoundIterator<String>(iterator, inner);
      }
      return iterator;
   }

   @Override
   public Value get(String name) {
      Value value = values.get(name);
      
      if(value == null) {
         State state = scope.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         value = state.get(name);
         
         if(value != null) {
            if(!value.isProperty()) { // this does not really work
               Object object = value.getValue();
               Value constant = ValueType.getConstant(object);
               
               values.put(name, constant); // cache as constant
            }
         }
      }
      return value;
   }
   
   @Override
   public Value get(Address address) {
      String name = address.getName();
      Value value = values.get(name);
      
      if(value == null) {
         State state = scope.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         value = state.get(name);
         
         if(value != null) {
            if(!value.isProperty()) { // this does not really work
               Object object = value.getValue();
               Value constant = ValueType.getConstant(object);
               
               values.put(name, constant); // cache as constant
            }
         }
      }
      return value;
   }

   @Override
   public void set(Address address, Value value) {
      String name = address.getName();
      Value variable = values.get(name);
      
      if(variable == null && scope != null) {
         State state = scope.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         variable = state.get(name);
      }
      Object data = value.getValue();

      if(variable == null) {
         throw new InternalStateException("Variable '" + name + "' does not exist");
      }
      variable.setValue(data);      
   }
   
   @Override
   public Address add(String name, Value value) {
      Value variable = values.get(name);

      if(variable != null) {
         throw new InternalStateException("Variable '" + name + "' already exists");
      }
      values.put(name, value); 
      return new Address(name, null, -1);
   }
   
   @Override
   public String toString() {
      return String.valueOf(values);
   }
}
