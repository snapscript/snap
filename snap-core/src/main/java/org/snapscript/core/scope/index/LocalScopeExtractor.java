package org.snapscript.core.scope.index;

import org.snapscript.core.constraint.Constraint;
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
            String name = local.getName();
            Value existing = inner.getValue(name);
            
            if(existing == null) {
               if(reference) {
                  inner.addValue(name, local); // enable modification of local
               } else {
                  Object value = local.getValue();
                  Constraint constraint = local.getConstraint();
                  Value constant = Value.getConstant(value, constraint);
                  
                  inner.addValue(name, constant); // local is a visible constant
               }
            }
         }
      }
      return capture;
   }
}