package org.snapscript.core;

import static org.snapscript.core.Reserved.TYPE_THIS;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.variable.Value;

public class ThisBinder {
   
   public ThisBinder() {
      super();
   }

   public Scope bind(Scope scope, Scope instance) {
      if(instance != null) {
         State state = instance.getState();
         Value value = state.get(TYPE_THIS);
         
         if(value != null) {
            return value.getValue();
         }
      }
      return scope;
   }
}