package org.snapscript.core.constraint;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;

class ObjectConstraint extends Constraint {

   private final Object value;
   
   public ObjectConstraint(Object value) {
      this.value = value;
   }
   
   public Type getType(Scope scope){
      Module module = scope.getModule();

      if(value != null) {
         Class require = value.getClass();
         return module.getType(require); 
      }
      return null;
   }
}