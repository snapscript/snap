package org.snapscript.core.define;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.snapscript.common.CompoundIterator;
import org.snapscript.core.Bug;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.State;
import org.snapscript.core.Value;

public class InstanceState implements State {
   
   private final Map<String, Integer> locals;
   private final Map<String, Value> values;
   private final List<Value> stack;
   private final Instance instance;

   public InstanceState(Instance instance) {
      this(instance, null);
   }
   
   public InstanceState(Instance instance, List<Value> stack) {
      this.locals = new HashMap<String, Integer>();
      this.values = new HashMap<String, Value>();
      this.stack = stack == null ? new ArrayList<Value>() : stack;
      this.instance = instance;
   }
   
   public List<Value> getStack(){
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
   public Value getScope(String name) {
      Value value = values.get(name);
      
      if(value == null) {
         State state = instance.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         value = state.getScope(name);
      }
      return value;
   }
   
   @Override
   public void addScope(String name, Value value) {
      Value variable = values.get(name);

      if(variable != null) {
         throw new InternalStateException("Variable '" + name + "' already exists");
      }
      values.put(name, value); 
   }
   
   @Bug("fix local value get")
   @Override
   public Value getLocal(int index) {
      return stack.get(index);
   }
   
   @Override
   public void addLocal(int index, Value value) {
      int size = stack.size();
      if(index >= size) {
         for(int i = size; i <= index; i++){
            stack.add(null);
         }
      }
      stack.set(index, value);
   }
   
   @Bug("fix local value get")
   @Override
   public int getLocal(String name) {
      Integer index = locals.get(name);
      if(index != null){
         return index;
      }
      return -1;
   }
   
   @Override
   public int addLocal(String name) {
      int index = locals.size();
      locals.put(name, index);
      return index;
   }
   
   @Override
   public Set<String> getLocals(){
      return locals.keySet();
   }
   
   @Override
   public int getDepth(){
      return locals.size();
   }
   
   @Override
   public String toString() {
      return String.valueOf(values);
   }
}