package org.snapscript.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.snapscript.common.CompoundIterator;

public class MapState implements State {
   
   private final Map<String, Integer> locals;
   private final Map<String, Value> values;
   private final List<Local> stack;
   private final Scope scope;
   private final Model model;
   
   public MapState() {
      this(null);
   }
  
   public MapState(Model model) {
      this(model, null);
   }

   @Bug("set state")
   public MapState(Model model, Scope scope) {
      this(model, scope, null);
   }
   
   @Bug("set state")
   public MapState(Model model, Scope scope, List<Local> stack) {
      this.locals = new HashMap<String, Integer>();
      this.values = new HashMap<String, Value>();
      this.stack = stack == null  ? new ArrayList<Local>(1): stack;
      this.model = model;
      this.scope = scope;
   }
   
   public List<Local> getStack(){
      return stack;
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
   public Value getScope(String name) {
      Value value = values.get(name);
      
      if(value == null && scope != null) {
         State state = scope.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         value = state.getScope(name);
      }
      if(value == null && model != null) {
         Object object = model.getAttribute(name);
         
         if(object != null) {
            return Value.getConstant(object);
         }
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
   public Local getLocal(int index) {
      try {
         return stack.get(index);
      }catch(RuntimeException e){
         e.printStackTrace();
         throw e;
      }
   }
   
   @Override
   public void addLocal(int index, Local value) {
      if(value == null) {
         throw new IllegalStateException("Local was null");
      }
      int size = stack.size();
      if(index >= size) {
         for(int i = size; i <= index; i++){
            stack.add(null);
         }
      }
      try{
         stack.set(index, value);
      }catch(Exception e){
         e.printStackTrace();
      }
   }
   
   @Override
   public String toString() {
      return String.valueOf(values);
   }
}