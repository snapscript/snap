package org.snapscript.core.closure;

import java.util.Iterator;
import java.util.Set;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Bug;
import org.snapscript.core.Model;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;

public class ClosureScopeExtractor {

   private final Cache<String, Integer> indexes;
   
   public ClosureScopeExtractor() {
      this.indexes = new CopyOnWriteCache<String, Integer>();
   }
   
   public void compile(Scope scope) {
      State state = scope.getState();
      Set<String> names = state.getLocals();
      
      for(String name : names) {
         int index = state.getLocal(name);
         indexes.cache(name, index);
      }
   }
   
   @Bug("getting wrong locals i.e the closure itself is being added")
   public Scope extract(Scope scope) {
      Model model = scope.getModel();
      State state = scope.getState();
      Scope outer = scope.getOuter();
      Iterator<String> names = state.iterator();
      Set<String> locals = indexes.keySet();
      
      if(names.hasNext() || !locals.isEmpty()) {
         Scope capture = new ClosureScope(model, scope, outer);
         
         for(String local : locals){
            int index = indexes.fetch(local);
            Value value = scope.getState().getLocal(index);
            
            capture.getState().addScope(local, value);
         }
         while(names.hasNext()) {
            String name = names.next();
            State inner = capture.getState();
            
            inner.getScope(name); // pull from outer scope
         }
         return capture;
      }
      return scope;
   }
}