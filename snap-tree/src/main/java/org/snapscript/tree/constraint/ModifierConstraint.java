package org.snapscript.tree.constraint;

import org.snapscript.core.ModifierType;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;

public class ModifierConstraint extends Constraint {
   
   private final Constraint constraint;
   private final int modifiers;
   
   public ModifierConstraint(Constraint constraint) {
      this(constraint, 0);
   }
   
   public ModifierConstraint(Constraint constraint, int modifiers) {      
      this.constraint = constraint;
      this.modifiers = modifiers;
   }
   
   public ModifierConstraint getConstraint(Scope scope, int mask) {
      if(mask != modifiers) {
         return new ModifierConstraint(constraint, mask);
      }
      return this;
   }
   
   @Override
   public Type getType(Scope scope) {
      if(constraint != null) {
         return constraint.getType(scope);
      }
      return null;
   }
   
   @Override
   public boolean isVariable() {
      if(constraint != null) {
         return constraint.isVariable();
      }
      return true;
   }
   
   @Override
   public boolean isStatic() {
      if(constraint != null) {
         return constraint.isStatic();
      }
      return false;
   }
   
   @Override
   public boolean isModule() {
      if(constraint != null) {
         return constraint.isModule();
      }
      return false;
   }
   
   @Override
   public boolean isConstant() {
      if(constraint != null) {
         return constraint.isConstant();
      }
      return ModifierType.isConstant(modifiers);
   }
}