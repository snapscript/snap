package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.DeclarationAllocator;

public class MemberFieldAllocator extends DeclarationAllocator {

   public MemberFieldAllocator(Constraint constraint, Evaluation expression) {   
      super(constraint, expression);
   }
   
   @Override
   protected <T extends Value> T declare(Scope scope, String name, Constraint type, int modifiers) throws Exception {
      Type source = scope.getType();
      
      if(ModifierType.isConstant(modifiers)) {
         return (T)Value.getBlank(null, source, type, modifiers);
      }
      return (T)Value.getProperty(null, source, type, modifiers);
   }
   
   @Override
   protected <T extends Value> T assign(Scope scope, String name, Object value, Constraint type, int modifiers) throws Exception {
      Type source = scope.getType();
      
      if(ModifierType.isConstant(modifiers)) {
         return (T)Value.getConstant(value, source, type, modifiers);
      }
      return (T)Value.getProperty(value, source, type, modifiers);
   }
}