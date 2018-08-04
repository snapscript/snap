package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.DeclarationAllocator;

public class ModulePropertyAllocator extends DeclarationAllocator {

   public ModulePropertyAllocator(Constraint constraint, Evaluation expression) {   
      super(constraint, expression);
   }
   
   @Override
   protected <T extends Value> T declare(Scope scope, String name, Constraint type, int modifiers) throws Exception {
      Module module = scope.getModule();
      
      if(ModifierType.isConstant(modifiers)) {
         return (T)Value.getBlank(null, module, type, modifiers);
      }
      return (T)Value.getProperty(null, module, type, modifiers);
   }
   
   @Override
   protected <T extends Value> T assign(Scope scope, String name, Object value, Constraint type, int modifiers) throws Exception {
      Module module = scope.getModule();
      
      if(ModifierType.isConstant(modifiers)) {
         return (T)Value.getConstant(value, module, type, modifiers);
      }
      return (T)Value.getProperty(value, module, type, modifiers);
   }
}