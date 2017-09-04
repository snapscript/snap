package org.snapscript.core;

import java.util.List;

public class LocalScopeExtractor {
   
   public LocalScopeExtractor() {
      super();
   }

   public Scope extract(Scope scope) {
      Model model = scope.getModel();
      Scope outer = scope.getOuter();
      
      return extract(scope, outer, model);
   }
   
   private Scope extract(Scope original, Scope outer, Model model) {
      Scope capture = new LocalScope(model, original, outer);
      
      if(original != null) {
         State state = original.getState();
         State inner = capture.getState();
         List<Local> locals = state.getStack();
         
         for(Local local : locals){
            Object value = local.getValue();
            String name = local.getName();
            Value constant = Value.getConstant(value);
            
            inner.addScope(name, constant);
         }
      }
      return capture;
   }
}