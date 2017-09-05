package org.snapscript.core;

import java.util.List;

public class LocalScopeExtractor {
   
   private final boolean reference;
   private final boolean extension;
   
   public LocalScopeExtractor(boolean reference, boolean extension) {
      this.reference = reference;
      this.extension = extension;
   }

   public Scope extract(Scope scope) {
      Model model = scope.getModel();
      Scope outer = scope.getOuter();
      
      if(extension) {
         return extract(scope, outer, model); // can see callers scope
      }
      return extract(outer, outer, model); // can't see callers scope
   }
   
   private Scope extract(Scope original, Scope outer, Model model) {
      Scope capture = new LocalScope(model, original, outer);
      
      if(original != null) {
         State state = original.getState();
         State inner = capture.getState();
         List<Local> locals = state.getStack();
         
         for(Local local : locals){
            if(local != null) {
               String name = local.getName();
               
               if(reference) {
                  inner.addScope(name, local); // enable modification of local
               } else {
                  Object value = local.getValue();
                  Value constant = Value.getConstant(value);
                  
                  inner.addScope(name, constant); // local is a visible constant
               }
            }
         }
      }
      return capture;
   }
}