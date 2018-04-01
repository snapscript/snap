package org.snapscript.core.constraint;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class StaticConstraint extends Constraint {

   private final Type require;
   
   public StaticConstraint(Type require) {
      this.require = require;
   }
   
   @Override
   public Type getType(Scope scope){
      return require;
   }      
   
   @Override
   public boolean isStatic(){
      return true;
   }
   
   @Override
   public boolean isConstant(){
      return true;
   }   
}