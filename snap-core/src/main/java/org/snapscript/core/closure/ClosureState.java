package org.snapscript.core.closure;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
   public Set<String> getNames() {
      Set<String> names = new HashSet<String>();   
      
      if(scope != null) {
         State state = scope.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for does not exist");
         }
         Set<String> inner = state.getNames();
         Set<String> outer = values.keySet();
         
         names.addAll(inner);
         names.addAll(outer);
         
         return names;
      }
      return values.keySet();
   }

   @Override
   public Value getValue(String name) {
      Value value = values.get(name);
      
      if(value == null) {
         State state = scope.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         value = state.getValue(name);
         
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
   public void setValue(String name, Value value) {
      Value variable = values.get(name);
      
      if(variable == null && scope != null) {
         State state = scope.getState();
         
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
   public void addValue(String name, Value value) {
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
