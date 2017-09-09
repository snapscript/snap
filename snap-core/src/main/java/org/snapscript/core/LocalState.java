package org.snapscript.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.snapscript.common.CompoundIterator;

public class LocalState implements State {
   
   private final Map<String, Value> values;
   private final Scope scope;

   public LocalState(Scope scope) {
      this(scope, null);
   }
   
   public LocalState(Scope scope, List<Local> stack) {
      this.values = new HashMap<String, Value>();
      this.scope = scope;
   }

   @Override
   public Iterator<String> iterator() {
      Set<String> keys = values.keySet();
      Iterator<String> inner = keys.iterator();
      
      if(scope != null) {
         State state = scope.getState();
         Iterator<String> outer = state.iterator();
         
         return new CompoundIterator<String>(inner, outer);
      }
      return inner;
   }

   @Override
   public Value get(String name) {
      Value value = values.get(name);
      
      if(value == null && scope != null) {
         State state = scope.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         return state.get(name);
      }
      return value;
   }
   
   @Override
   public void add(String name, Value value) {
      Value variable = values.get(name);

      if(variable != null) {
         throw new InternalStateException("Variable '" + name + "' already exists");
      }
      values.put(name, value);  
   }
   
   @Override
   public String toString() {
      return String.valueOf(values);
   }
}