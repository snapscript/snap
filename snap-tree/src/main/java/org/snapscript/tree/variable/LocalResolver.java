package org.snapscript.tree.variable;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.tree.reference.TypeReferenceFinder;

public class LocalResolver implements ValueResolver<Object> {
   
   private final AtomicReference<Object> reference;
   private final TypeReferenceFinder finder;
   private final String name;
   
   public LocalResolver(String name) {
      this.reference = new AtomicReference<Object>();
      this.finder = new TypeReferenceFinder();
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
   
   private Object match(Scope scope, Object left) {
      Module module = scope.getModule();
      Type type = module.getType(name);
      Type parent = scope.getType();
      
      if(type == null) {
         Object result = module.getModule(name);
         
         if(result == null && parent != null) {
            result = finder.findType(parent, name);
         }
         return result;
      }
      return type;
   }
}