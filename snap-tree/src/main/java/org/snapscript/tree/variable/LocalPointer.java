package org.snapscript.tree.variable;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Bug;
import org.snapscript.core.Constraint;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;

public class LocalPointer implements VariablePointer<Object> {
   
   private final AtomicReference<Object> reference;
   private final ConstantResolver resolver;
   private final String name;
   
   public LocalPointer(ConstantResolver resolver, String name) {
      this.reference = new AtomicReference<Object>();
      this.resolver = resolver;
      this.name = name;
   }

   @Bug("rubbish")
   @Override
   public Constraint check(Scope scope, Constraint left) {
      Object result = reference.get();
      
      if(result == null) {
         State state = scope.getState();
         Value variable = state.get(name);
         
         if(variable == null) { 
            //THIS LINE HERE IS LOADING TONNES OF CLASSES BY NAME... this is not good
            Object value = resolver.resolve(scope, name);
            
            if(value instanceof Type) {
               reference.set(value);
               return Constraint.getStatic((Type)value);
            }
            if(value instanceof Module) {
               reference.set(value);
               return Constraint.getModule((Module)value);
            }
            if(value != null) {
               reference.set(value);
               return Constraint.getInstance(value);
            }
            return null;
         }
         return Constraint.getInstance(variable.getValue());
      }
      if(result instanceof Type) {
         return Constraint.getStatic((Type)result);
      }
      if(result instanceof Module) {
         return Constraint.getModule((Module)result);
      }
      return Constraint.getInstance(result);
   }
   
   @Override
   public Value get(Scope scope, Object left) {
      Object result = reference.get();
      
      if(result == null) {
         State state = scope.getState();
         Value variable = state.get(name);
         
         if(variable == null) { 
            Object value = resolver.resolve(scope, name);
            
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