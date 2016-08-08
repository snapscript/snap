package org.snapscript.core.define;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;

public class StaticState implements State {
   
   private final Map<String, Value> values;
   private final Instance base;
   private final Scope inner;

   public StaticState(Scope inner, Instance base) {
      this.values = new HashMap<String, Value>();
      this.inner = inner;
      this.base = base;
   }
   
   @Override
   public Set<String> getNames() {
      Set<String> names = new HashSet<String>();   
      
      if(inner != null) {
         State state = inner.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for does not exist");
         }
         Set<String> inner = state.getNames();
         
         if(!inner.isEmpty()) {
            names.addAll(inner);
         }
      }
      Set<String> outer = values.keySet();
      
      if(!outer.isEmpty()) {
         names.addAll(outer);
      }
      return names;
   }

   @Override
   public Value getValue(String name) {
      Value value = values.get(name);
      
      if(value == null && inner != null) {
         State state = inner.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         value = state.getValue(name);
      }
      if(value == null && base != null) {
         State state = base.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         value = state.getValue(name);
      }
      return value;
   }

   @Override
   public void setValue(String name, Value value) {
      Value variable = values.get(name);
      
      if(variable == null && inner != null) {
         State state = inner.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         variable = state.getValue(name);
      }
      Object data = value.getValue();

      if(variable == null) {
         throw new InternalStateException("Variable '" + name + "' does not exist");
      }
      variable.setValue(data);      
   }
   
   @Override
   public void addVariable(String name, Value value) {
      Value variable = values.get(name);

      if(variable != null) {
         throw new InternalStateException("Variable '" + name + "' already exists");
      }
      values.put(name, value);      
   }
   
   @Override
   public void addConstant(String name, Value value) {
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
