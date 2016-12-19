package org.snapscript.core.closure;

import java.util.Set;

import org.snapscript.core.Model;
import org.snapscript.core.Scope;
import org.snapscript.core.State;

public class ClosureScopeExtractor {

   public ClosureScopeExtractor() {
      super();
   }
   
   public Scope extract(Scope scope) {
      Model model = scope.getModel();
      State state = scope.getState();
      Set<String> names = state.getNames();
      
      if(!names.isEmpty()) {
         Scope capture = new ClosureScope(model, scope);
         
         for(String name : names) {
            State inner = capture.getState();
            inner.getValue(name);
         }
         return capture;
      }
      return scope;
   }
}
