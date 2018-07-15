package org.snapscript.core.scope.index;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.variable.Value;

public class LocalScopeFinder {
   
   private final LocalScopeChecker checker;
   
   public LocalScopeFinder() {
      this.checker = new LocalScopeChecker();
   }   

   public Value findValue(Scope scope, String name) {
      return findValue(scope, name, -1);
   }

   public Value findValue(Scope scope, String name, int depth) {
      if(depth == -1){
         State state = scope.getState();
         Value value = state.get(name);
         
         if(checker.isValid(value)) { 
            return value;
         }
      }else {
         Table table = scope.getTable();
         Value value = table.get(depth);

         if(checker.isValid(value)) { 
            return value;
         }
      }
      return null;
   }
   
   public Value findFunction(Scope scope, String name) {
      return findFunction(scope, name, -1);
   }
   
   public Value findFunction(Scope scope, String name, int depth) {
      if(depth == -1){
         State state = scope.getState();
         Value value = state.get(name);
         
         if(!checker.isGenerated(value)) { 
            return value;
         }
      }else {
         Table table = scope.getTable();
         Value value = table.get(depth);

         if(!checker.isGenerated(value)) { 
            return value;
         }
      }
      return null;
   }
}
