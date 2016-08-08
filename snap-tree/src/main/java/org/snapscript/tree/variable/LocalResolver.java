package org.snapscript.tree.variable;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;

public class LocalResolver implements ValueResolver<Object> {
   
   private final AtomicReference<Object> reference;
   private final String name;
   
   public LocalResolver(String name) {
      this.reference = new AtomicReference<Object>();
      this.name = name;
   }
   
   @Override
   public Value resolve(Scope scope, Object left) {
      Object result = reference.get();
      
      if(result == null) {
         State state = scope.getState();
         Value variable = state.getValue(name);
         
         if(variable == null) { 
            Object value = match(scope, left);
            
            if(value != null) {
               reference.set(value);
               return ValueType.getTransient(value);
            }
         }
         return variable;
      }
      return ValueType.getTransient(result);
   }
   
   public Object match(Scope scope, Object left) {
      Module module = scope.getModule();
      Type type = module.getType(name);
      
      if(type == null) {
         Object result = module.getModule(name);
         
         if(result != null) {
            return result;
         }
         return null;
      }
      return type;
   }
}