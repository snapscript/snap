package org.snapscript.core.variable.index;

import static org.snapscript.core.ModifierType.CONSTANT;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.bind.VariableFinder;
import org.snapscript.core.variable.bind.VariableResult;

public class ModulePointer implements VariablePointer<Module> {
   
   private final AtomicReference<VariableResult> reference;
   private final VariableFinder finder;
   private final String name;
   
   public ModulePointer(VariableFinder finder, String name) {
      this.reference = new AtomicReference<VariableResult>();
      this.finder = finder;
      this.name = name;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      VariableResult result = reference.get();
      
      if(result == null) {
         Type parent = left.getType(scope);
         Module module = parent.getModule();
         State state = scope.getState();
         Value value = state.get(name);
         
         if(value == null) {
            Type type = module.getType(name);
            
            if(type == null) {
               VariableResult match = finder.findAll(scope, module, name);
               
               if(match != null) {
                  reference.set(match);
                  return match.getConstraint(left);
               }
               return null;
            }
            return Constraint.getConstraint(type, CONSTANT.mask);
         }
         return value.getConstraint();
      } 
      return result.getConstraint(left);
   }
   
   @Override
   public Value getValue(Scope scope, Module left) {
      VariableResult result = reference.get();
      
      if(result == null) {
         Scope inner = left.getScope();
         State state = inner.getState();
         Value value = state.get(name);
         
         if(value == null) {
            Type type = left.getType(name);
            
            if(type == null) {            
               VariableResult match = finder.findAll(scope, left, name);
               
               if(match != null) {
                  reference.set(match);
                  return match.getValue(left);
               }
               return null;
            }
            return Value.getTransient(type, left);
         }
         return value;
      } 
      return result.getValue(left);
   }
   
}