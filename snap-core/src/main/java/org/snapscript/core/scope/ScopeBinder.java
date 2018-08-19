package org.snapscript.core.scope;

import static org.snapscript.core.Reserved.TYPE_THIS;

import org.snapscript.core.variable.Value;

public class ScopeBinder {
   
   public ScopeBinder() {
      super();
   }

   public Scope bind(Scope scope, Scope instance) {
      if(instance != null) {
         State state = instance.getState();
         Value value = state.getValue(TYPE_THIS);
         
         if(value != null) {
            return value.getValue();
         }
      }
      return scope;
   }
}