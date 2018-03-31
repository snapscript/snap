package org.snapscript.core.constraint;

public class ConstantConstraint extends ClassConstraint {
   
   public ConstantConstraint(Class type) {
      super(type);
   }
   
   @Override
   public boolean isConstant() {
      return true;
   }
   
}
