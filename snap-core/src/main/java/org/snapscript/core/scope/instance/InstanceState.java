package org.snapscript.core.scope.instance;

import java.util.Iterator;
import java.util.Set;

import org.snapscript.common.Cache;
import org.snapscript.common.CompoundIterator;
import org.snapscript.common.HashCache;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.State;
import org.snapscript.core.variable.Value;

public class InstanceState implements State {
   
   private final Cache<String, Constraint> constraints;
   private final Cache<String, Value> values;   
   private final Instance instance;

   public InstanceState(Instance instance) {
      this.constraints = new HashCache<String, Constraint>();
      this.values = new HashCache<String, Value>();
      this.instance = instance;
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
   public Value getValue(String name) {
      Value value = values.fetch(name);
      
      if(value == null) {
         State state = instance.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         return state.getValue(name);
      }
      return value;
   }
   
   @Override
   public void addValue(String name, Value value) {
      Value existing = values.fetch(name);

      if(existing != null) {
         throw new InternalStateException("Variable '" + name + "' already exists");
      }
      values.cache(name, value); 
   }
   
   @Override
   public Constraint getConstraint(String name) {
      Constraint constraint = constraints.fetch(name);
      
      if(constraint == null) {
         State state = instance.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         return state.getConstraint(name);
      }
      return constraint;
   }
   
   @Override
   public void addConstraint(String name, Constraint constraint) {
      Constraint existing = constraints.fetch(name);

      if(existing != null) {
         throw new InternalStateException("Constraint '" + name + "' already exists");
      }
      constraints.cache(name, constraint); 
   }
   
   @Override
   public String toString() {
      return String.valueOf(values);
   }
}