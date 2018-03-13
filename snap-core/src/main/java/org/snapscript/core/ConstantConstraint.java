package org.snapscript.core;

import static org.snapscript.core.ConstraintType.INSTANCE;

public class ConstantConstraint extends Constraint {
   
   private final Type type;
   private final int mask;
   
   public ConstantConstraint(Type type) {
      this(type, INSTANCE.mask);
   }
   
   public ConstantConstraint(Type type, int mask) {
      this.type = type;
      this.mask = mask;
   }

   @Override
   public Type getType(Scope scope) {
      return type;
   }
   
   @Override
   public boolean isStatic(){
      return ConstraintType.isStatic(mask);
   }
   
   @Override
   public boolean isInstance() {
      return ConstraintType.isInstance(mask);
   }

}
