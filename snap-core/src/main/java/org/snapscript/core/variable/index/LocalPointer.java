package org.snapscript.core.variable.index;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.bind.VariableFinder;
import org.snapscript.core.variable.bind.VariableResult;

public class LocalPointer implements VariablePointer<Object> {
   
   private final AtomicReference<VariableResult> reference;
   private final VariableFinder finder;
   private final String name;
   
   public LocalPointer(VariableFinder finder, String name) {
      this.reference = new AtomicReference<VariableResult>();
      this.finder = finder;
      this.name = name;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      VariableResult result = reference.get();
      
      if(result == null) {
         State state = scope.getState();
         Value variable = state.get(name);
         
         if(variable == null) { 
            VariableResult match = finder.findType(scope, name);
            
            if(match != null) {
               return match.getConstraint(left); 
            }
            return null;
         }
         return variable;
      }
      return result.getConstraint(left);
   }
   
   @Override
   public Value getValue(Scope scope, Object left) {
      VariableResult result = reference.get();
      
      if(result == null) {
         State state = scope.getState();
         Value variable = state.get(name);
         
         if(variable == null) { 
            VariableResult match = finder.findType(scope, name);
            
            if(match != null) {
               reference.set(match);
               return match.getValue(left);
            }
         }
         return variable;
      }
      return result.getValue(left);
   }
}