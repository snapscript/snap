package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.tree.DeclarationAllocator;
import org.snapscript.tree.constraint.Constraint;

public class MemberFieldAllocator extends DeclarationAllocator {

   public MemberFieldAllocator(Constraint constraint, Evaluation expression) {   
      super(constraint, expression);
   }
   
   @Override
   protected Value create(Scope scope, Object value, Type type, int modifiers) throws Exception {
      if(ModifierType.isConstant(modifiers)) {
         return ValueType.getBlank(value, type, modifiers);
      }
      return ValueType.getProperty(value, type, modifiers);
   }
}
