package org.snapscript.core.constraint;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class ClassConstraint extends Constraint {

   private final Class require;
   
   public ClassConstraint(Class require) {
      this.require = require;
   }
   
   public Type getType(Scope scope){
      if(require != null) {
         Module module = scope.getModule();
         return module.getType(require);
      }
      return null;
   }
}