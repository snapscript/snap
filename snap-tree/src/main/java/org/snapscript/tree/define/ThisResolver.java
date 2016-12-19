package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_THIS;

import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;

public class ThisResolver {
   
   public ThisResolver() {
      super();
   }

   public Scope resolve(Scope scope, Scope instance) {
      if(instance != null) {
         State state = instance.getState();
         Value value = state.getValue(TYPE_THIS);
         
         return value.getValue();
      }
      return scope;
   }
}
