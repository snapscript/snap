package org.snapscript.core.define;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.snapscript.core.Address;
import org.snapscript.core.Bug;
import org.snapscript.core.CompoundIterator;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.State;
import org.snapscript.core.Value;

public class InstanceState implements State {
   
   private final Map<String, Value> values;
   private final Instance instance;

   public InstanceState(Instance instance) {
      this.values = new HashMap<String, Value>();
      this.instance = instance;
   }
   
   @Override
   public Address address(String name) {
      return new Address(name, null, -1);
   }
   
   @Bug
   @Override
   public boolean contains(String name) {
      return values.containsKey(name);
   }
   
   @Override
   public Iterator<String> iterator() {
      Set<String> names = values.keySet();
      Iterator<String> iterator =  names.iterator();
      
      if(instance != null) {
         State state = instance.getState();
         Iterator<String> inner = state.iterator();
         
         return new CompoundIterator<String>(iterator, inner);
      }
      return iterator;
   }

   @Override
   public Value get(String name) {
      Value value = values.get(name);
      
      if(value == null) {
         State state = instance.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         value = state.get(name);
      }
      return value;
   }

   @Override
   public Value get(Address address) {
      String name = address.getName();
      Value value = values.get(name);
      
      if(value == null) {
         State state = instance.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         value = state.get(name);
      }
      return value;
   }
   
   @Override
   public void set(Address address, Value value) {
      String name = address.getName();
      Value variable = values.get(name);
      
      if(variable == null && instance != null) {
         State state = instance.getState();
         
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
