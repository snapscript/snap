package org.snapscript.core.scope.index;

import java.util.HashSet;
import java.util.Set;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.variable.Value;

public class CaptureScopeExtractor {
   
   private final CaptureType type;

   public CaptureScopeExtractor(CaptureType type) {
      this.type = type;
   }

   public Scope extract(Scope scope) {
      Scope outer = scope.getScope();
      
      if(type.isExtension()) {
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
            Constraint constraint = local.getConstraint();

            if(!type.isGlobals() || constraint.isStatic()) {
               Value value = inner.getValue(name);

               if(value == null) {
                  if (type.isReference()) {
                     inner.addValue(name, local); // enable modification of local
                  } else {
                     Object object = local.getValue();
                     Value constant = Value.getConstant(object, constraint);

                     inner.addValue(name, constant); // local is a visible constant
                  }
               }
            }
         }
      }
      return capture;
   }
}