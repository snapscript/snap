package org.snapscript.core.constraint;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public class ModuleConstraint extends Constraint {
   
   private final Module module;
   
   public ModuleConstraint(Module module) {
      this.module = module;
   }
   
   @Override
   public Type getType(Scope scope){
      return module.getType();
   }
   
   @Override
   public boolean isModule() {
      return true;
   }
   
   @Override
   public boolean isConstant() {
      return true;
   }
}