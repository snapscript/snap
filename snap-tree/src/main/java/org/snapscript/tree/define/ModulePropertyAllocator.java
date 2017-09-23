package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.tree.DeclarationAllocator;
import org.snapscript.tree.constraint.Constraint;

public class ModulePropertyAllocator extends DeclarationAllocator {

   public ModulePropertyAllocator(Constraint constraint, Evaluation expression) {   
      super(constraint, expression);
   }
   
   @Override
   protected <T extends Value> T create(Scope scope, String name, Object value, Type type, int modifiers) throws Exception {
      if(ModifierType.isConstant(modifiers)) {
         return (T)Value.getBlank(value, type, modifiers);
      }
      return (T)Value.getProperty(value, type, modifiers);
   }
}