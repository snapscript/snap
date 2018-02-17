package org.snapscript.core;

public class ConstantConstraint implements Constraint {
   
   private final Type type;
   
   public ConstantConstraint(Type type) {
      this.type = type;
   }

   @Override
   public Type getType(Scope scope) {
      return type;
   }

}
