package org.snapscript.core.scope.index;

import static org.snapscript.core.scope.index.AddressType.INSTANCE;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.ScopeState;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.bind.VariableFinder;
import org.snapscript.core.variable.bind.VariableResult;

public class AddressResolver {
   
   private final VariableFinder finder;
   private final Scope scope;

   public AddressResolver(Scope scope) {
      this.finder = new VariableFinder(null);
      this.scope = scope;
   }   

   public Address resolve(String name, int offset) {
      ScopeState state = scope.getState();
      Value value = state.getValue(name);
      
      if(value == null) { 
         Type type = scope.getType();
         
         if(type != null) {
            VariableResult result = finder.findAll(scope, type, name);
            
            if(result != null) {
               return result.getAddress(offset);
            }            
         }
         return null;
      }      
      return INSTANCE.getAddress(name, offset);
   }
}
