package org.snapscript.tree.variable;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.AnyType;
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

   @Override
   public Type check(Scope scope, Type left) {
      Object result = reference.get();
      
      if(result == null) {
         State state = scope.getState();
         Value variable = state.get(name);
         
         if(variable == null) { 
            Object value = resolver.resolve(scope, name);
            
            if(value != null) {
               reference.set(value);
               return new AnyType(scope);
            }
         }
         return variable.getConstraint();
      }
      return new AnyType(scope); 
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