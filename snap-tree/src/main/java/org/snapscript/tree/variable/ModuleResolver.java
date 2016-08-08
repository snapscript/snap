package org.snapscript.tree.variable;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;

public class ModuleResolver implements ValueResolver<Module> {
   
   private final String name;
   
   public ModuleResolver(String name) {
      this.name = name;
   }
   
   @Override
   public Value resolve(Scope scope, Module left) {
      Scope inner = left.getScope();
      State state = inner.getState();
      Value value = state.getValue(name);
      
      if(value == null) {
         Type type = left.getType(name);
        
         if(type != null) {
            return ValueType.getTransient(type);
         }
      }
      return value;
   }
}