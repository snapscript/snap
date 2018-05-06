package org.snapscript.core.constraint;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class PrimitiveConstraint extends Constraint {

   private final Class require;
   
   public PrimitiveConstraint(Class require) {
      this.require = require;
   }
   
   @Override
   public Type getType(Scope scope){
      if(require != null) {
         Module module = scope.getModule();
         return module.getType(require);
      }
      return null;
   }

   @Override
   public boolean isVariable(){
      return true;
   }
   
   @Override
   public String toString(){
      return String.valueOf(require);
   }
}