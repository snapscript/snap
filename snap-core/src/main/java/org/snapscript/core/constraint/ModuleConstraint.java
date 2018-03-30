package org.snapscript.core.constraint;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;

class ModuleConstraint extends Constraint {
   
   private final Module module;
   
   public ModuleConstraint(Module module) {
      this.module = module;
   }
   
   public Type getType(Scope scope){
      return module.getType();
   }
   
   public boolean isModule() {
      return true;
   }
}