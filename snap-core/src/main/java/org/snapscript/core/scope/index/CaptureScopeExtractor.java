package org.snapscript.core.scope.index;

import java.util.Iterator;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.ScopeState;
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
      ScopeTable table = original.getTable();
      Iterator<Value> iterator = table.iterator();

      if(iterator.hasNext() || !type.isCompiled()) {
         Scope capture = new LocalScope(original, outer);

         while(iterator.hasNext()) {
            Value local = iterator.next();
            String name = local.getName();
            Constraint constraint = local.getConstraint();

            if (!type.isGlobals() || constraint.isStatic()) {
               ScopeState inner = capture.getState();
               Value value = inner.getValue(name);

               if (value == null) {
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
         return capture;
      }
      return original;
   }
}