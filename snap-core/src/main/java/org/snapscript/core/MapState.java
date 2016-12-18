package org.snapscript.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapState implements State {
   
   private final Map<String, Value> values;
   private final Scope scope;
   private final Model model;
   
   public MapState() {
      this(null);
   }
  
   public MapState(Model model) {
      this(model, null);
   }
   
   public MapState(Model model, Scope scope) {
      this.values = new HashMap<String, Value>();
      this.model = model;
      this.scope = scope;
   }

   @Override
   public Address address(String name) {
      Value value = values.get(name);
      
      if(value == null && scope != null) {
         State state = scope.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         value = state.get(name);
      }
      if(value == null && model != null) {
         Object object = model.getAttribute(name);
         
         if(object != null) {
            return new Address(name, null, 0);
         }
         return new Address(name, null, -1);
      }
      return new Address(name, null, 0);
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
      
      if(value == null && scope != null) {
         State state = scope.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         value = state.get(name);
      }
      if(value == null && model != null) {
         Object object = model.getAttribute(name);
         
         if(object != null) {
            return ValueType.getConstant(object);
         }
      }
      return value;
   }
   
   @Override
   public Value get(Address address) {
      String name = address.getName();
      Value value = values.get(name);
      
      if(value == null && scope != null) {
         State state = scope.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         value = state.get(name);
      }
      if(value == null && model != null) {
         Object object = model.getAttribute(name);
         
         if(object != null) {
            return ValueType.getConstant(object);
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
