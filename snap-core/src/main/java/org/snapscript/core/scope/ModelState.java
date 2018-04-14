package org.snapscript.core.scope;

import java.util.Iterator;
import java.util.Set;

import org.snapscript.common.Cache;
import org.snapscript.common.CompoundIterator;
import org.snapscript.common.HashCache;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.variable.Value;

public class ModelState implements State {
   
   private final Cache<String, Value> values;
   private final Scope scope;
   private final Model model;
   
   public ModelState() {
      this(null);
   }
  
   public ModelState(Model model) {
      this(model, null);
   }

   public ModelState(Model model, Scope scope) {
      this.values = new HashCache<String, Value>();
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
   public Value get(String name) {
      Value value = values.fetch(name);
      
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
            return Value.getConstant(object);
         }
      }
      return value;
   }
   
   @Override
   public void add(String name, Value value) {
      Value variable = values.fetch(name);

      if(variable != null) {
         throw new InternalStateException("Variable '" + name + "' already exists");
      }
      values.cache(name, value);
   }
   
   @Override
   public String toString() {
      return String.valueOf(values);
   }
}