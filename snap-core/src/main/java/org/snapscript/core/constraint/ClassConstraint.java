package org.snapscript.core.constraint;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;

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