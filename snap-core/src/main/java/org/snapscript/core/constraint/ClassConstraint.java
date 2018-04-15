package org.snapscript.core.constraint;

import org.snapscript.core.ModifierType;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class ClassConstraint extends Constraint {

   private final Class require;
   private final int modifiers;
   
   public ClassConstraint(Class require) {
      this(require, 0);
   }
   
   public ClassConstraint(Class require, int modifiers) {
      this.modifiers = modifiers;
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
      return !ModifierType.isConstant(modifiers);
   }
   
   @Override
   public boolean isConstant(){
      return ModifierType.isConstant(modifiers);
   }
   
   @Override
   public boolean isClass(){
      return ModifierType.isClass(modifiers);
   }
}