package org.snapscript.core.constraint;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;

class StaticConstraint extends Constraint {

   private final Type require;
   
   public StaticConstraint(Type require) {
      this.require = require;
   }
   
   public Type getType(Scope scope){
      return require;
   }      
   
   @Override
   public boolean isStatic(){
      return true;
   }
}