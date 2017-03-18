
package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_THIS;

import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;

public class ThisScopeBinder {
   
   public ThisScopeBinder() {
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
