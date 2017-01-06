package org.snapscript.core.closure;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.snapscript.common.CompoundIterator;
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
