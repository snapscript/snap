package org.snapscript.core.define;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.snapscript.common.CompoundIterator;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Local;
import org.snapscript.core.State;
import org.snapscript.core.Value;

public class InstanceState implements State {
   
   private final Map<String, Value> values;
   private final List<Local> stack;
   private final Instance instance;

   public InstanceState(Instance instance) {
      this(instance, null);
   }
   
   public InstanceState(Instance instance, List<Local> stack) {
      this.values = new HashMap<String, Value>();
      this.stack = stack == null ? new ArrayList<Local>() : stack;
      this.instance = instance;
   }
   
   public List<Local> getStack(){
      return stack;
   }
   
   @Override
   public Iterator<String> iterator() {
      Set<String> keys = values.keySet();
      Iterator<String> inner = keys.iterator();
      
      if(instance != null) {
         State state = instance.getState();
         Iterator<String> outer = state.iterator();
         
         return new CompoundIterator<String>(inner, outer);
      }
      return inner;
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