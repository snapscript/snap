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
   private final List<Value> stack;
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
   public MapState(Model model, Scope scope, List<Value> stack) {
      this.locals = new HashMap<String, Integer>();
      this.values = new HashMap<String, Value>();
      this.stack = stack == null  ? new ArrayList<Value>(): stack;
      this.model = model;
      this.scope = scope;
   }
   
   public List<Value> getStack(){
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
            return ValueType.getConstant(object);
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
   public Value getLocal(int index) {
      try {
         return stack.get(index);
      }catch(RuntimeException e){
         e.printStackTrace();
         throw e;
      }
   }
   
   @Override
   public void addLocal(int index, Value value) {
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
      Integer ex=locals.get(name);
      if(ex==null){
         int index = locals.size();
         locals.put(name, index);
         return index;
      }
      return ex;
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