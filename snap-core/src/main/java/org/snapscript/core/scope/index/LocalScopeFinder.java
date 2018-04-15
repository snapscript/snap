package org.snapscript.core.scope.index;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.variable.Value;

public class LocalScopeFinder {
   
   public LocalScopeFinder() {
      super();
   }

   public Value find(Scope scope, String name, int depth) {
      if(depth == -1){
         State state = scope.getState();
         Value value = state.get(name);
         
         if(value != null) { 
            return value;
         }
      }else {
         Table table = scope.getTable();
         Value value = table.get(depth);

         if(value != null) { 
            return value;
         }
      }
      return null;
   }
}
