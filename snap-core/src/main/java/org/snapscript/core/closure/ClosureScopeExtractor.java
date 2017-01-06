package org.snapscript.core.closure;

import java.util.Iterator;

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
      Iterator<String> names = state.iterator();
      
      if(names.hasNext()) {
         Scope capture = new ClosureScope(model, scope);
         
         while(names.hasNext()) {
            State inner = capture.getState();
            String name = names.next();
            
            inner.get(name);
         }
         return capture;
      }
      return scope;
   }
}
