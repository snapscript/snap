package org.snapscript.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.snapscript.common.CompoundIterator;

public class ModelState implements State {
   
   private final Map<String, Value> values;
   private final Scope scope;
   private final Model model;
   
   public ModelState() {
      this(null);
   }
  
   public ModelState(Model model) {
      this(model, null);
   }

   public ModelState(Model model, Scope scope) {
      this.values = new HashMap<String, Value>();
      this.model = model;
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
   
   @Override
   public String toString() {
      return String.valueOf(values);
   }
}