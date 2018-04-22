package org.snapscript.core.scope.index;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.variable.Value;

public class LocalScopeExtractor {
   
   private final boolean reference;
   private final boolean extension;
   
   public LocalScopeExtractor(boolean reference, boolean extension) {
      this.reference = reference;
      this.extension = extension;
   }

   public Scope extract(Scope scope) {
      Scope outer = scope.getScope();
      
      if(extension) {
         return extract(scope, outer); // can see callers scope
      }
      return extract(outer, outer); // can't see callers scope
   }
   
   public Scope extract(Scope original, Scope outer) {
      Scope capture = new LocalScope(original, outer);
      
      if(original != null) {
         Table table = original.getTable();
         State inner = capture.getState();
         
         for(Local local : table){
            String name = local.getName(original);
            
            if(reference) {
               inner.add(name, local); // enable modification of local
            } else {
               Object value = local.getValue();
               Value constant = Value.getConstant(value, local);
               
               inner.add(name, constant); // local is a visible constant
            }
         }
      }
      return capture;
   }
}