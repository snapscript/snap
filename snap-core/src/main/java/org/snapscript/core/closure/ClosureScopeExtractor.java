package org.snapscript.core.closure;

import java.util.Iterator;

import org.snapscript.core.Context;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.State;
import org.snapscript.core.thread.ThreadStack;

public class ClosureScopeExtractor {

   public ClosureScopeExtractor() {
      super();
   }
   
   public Scope extract(Scope scope) {
      Model model = scope.getModel();
      State state = scope.getState();
      Iterator<String> names = state.iterator();
      
      // TODO change to scope.getStack()
      Module module = scope.getModule();
      Context context = module.getContext();
      ThreadStack stack = context.getStack();
      State state2 = stack.state();
      
      if(!names.hasNext()) {
         Scope capture = new ClosureScope(state2, model, scope);
         
         for(String name : state) {
            State inner = capture.getState();
            inner.get(name); // pull up
         }
         return capture;
      }
      return scope;
   }
}
