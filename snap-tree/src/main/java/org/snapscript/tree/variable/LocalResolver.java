package org.snapscript.tree.variable;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;

public class LocalResolver implements ValueResolver<Object> {
   
   private final AtomicReference<Object> reference;
   private final ModuleConstantFinder matcher;
   private final String name;
   
   public LocalResolver(String name) {
      this.reference = new AtomicReference<Object>();
      this.matcher = new ModuleConstantFinder();
      this.name = name;
   }
   
   @Override
   public Value resolve(Scope scope, Object left) {
      Object result = reference.get();
      
      if(result == null) {
         State state = scope.getState();
         Value variable = state.get(name);
         
         if(variable == null) { 
            Object value = matcher.find(scope, name);
            
            if(value != null) {
               reference.set(value);
               return ValueType.getTransient(value);
            }
         }
         return variable;
      }
      return ValueType.getTransient(result);
   }
}