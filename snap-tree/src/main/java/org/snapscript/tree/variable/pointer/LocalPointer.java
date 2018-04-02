package org.snapscript.tree.variable.pointer;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.Value;
import org.snapscript.tree.variable.VariableConstraintMapper;
import org.snapscript.tree.variable.VariableFinder;

public class LocalPointer implements VariablePointer<Object> {
   
   private final AtomicReference<Object> reference;
   private final VariableConstraintMapper mapper;
   private final VariableFinder finder;
   private final String name;
   
   public LocalPointer(VariableFinder finder, String name) {
      this.reference = new AtomicReference<Object>();
      this.mapper = new VariableConstraintMapper();
      this.finder = finder;
      this.name = name;
   }

   @Override
   public Constraint check(Scope scope, Constraint left) {
      Object result = reference.get();
      
      if(result == null) {
         State state = scope.getState();
         Value variable = state.get(name);
         
         if(variable == null) { 
            Object value = finder.findTypes(scope, name);
            
            if(value != null) {
               return mapper.toConstraint(value);
            }
            return null;
         }
         return Constraint.getConstraint(variable);
      }
      return mapper.toConstraint(result);
   }
   
   @Override
   public Value get(Scope scope, Object left) {
      Object result = reference.get();
      
      if(result == null) {
         State state = scope.getState();
         Value variable = state.get(name);
         
         if(variable == null) { 
            Object value = finder.findTypes(scope, name);
            
            if(value != null) {
               reference.set(value);
               return Value.getTransient(value);
            }
         }
         return variable;
      }
      return Value.getTransient(result);
   }
}