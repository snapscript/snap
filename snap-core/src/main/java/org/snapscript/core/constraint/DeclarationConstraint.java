package org.snapscript.core.constraint;

import org.snapscript.core.ModifierType;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class DeclarationConstraint extends Constraint {
   
   private final String name;
   private final Type type;
   private final int modifiers;

   public DeclarationConstraint(Type type) {
      this(type, null, 0);
   }
   
   public DeclarationConstraint(Type type, int modifiers) {
      this(type, null, modifiers);
   }
   
   public DeclarationConstraint(Type type, String name) {
      this(type, name, 0);
   }
   
   public DeclarationConstraint(Type type, String name, int modifiers) {
      this.modifiers = modifiers;
      this.type = type;
      this.name = name;
   }

   @Override
   public Type getType(Scope scope) {
      return type;
   }
   
   @Override
   public String getName(Scope scope) {
      return name;
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
   
   @Override
   public String toString() {
      return String.valueOf(type);
   }
}
