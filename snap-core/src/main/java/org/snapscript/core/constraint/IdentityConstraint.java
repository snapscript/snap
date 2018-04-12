package org.snapscript.core.constraint;

import org.snapscript.core.ModifierType;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class IdentityConstraint extends Constraint {
   
   private final Type type;
   private final int modifiers;
   
   public IdentityConstraint(Type type) {
      this(type, 0);
   }
   
   public IdentityConstraint(Type type, int modifiers) {
      this.modifiers = modifiers;
      this.type = type;
   }

   @Override
   public Type getType(Scope scope) {
      return type;
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
