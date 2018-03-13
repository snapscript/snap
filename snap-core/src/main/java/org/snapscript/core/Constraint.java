package org.snapscript.core;

import static org.snapscript.core.ConstraintType.INSTANCE;
import static org.snapscript.core.ConstraintType.STATIC;

public abstract class Constraint {

   public static Constraint getNone() {
      return new ConstantConstraint(null);
   }
   
   public static Constraint getInstance(Type type) {
      return new ConstantConstraint(type, INSTANCE.mask);
   }
   
   public static Constraint getStatic(Type type) {
      return new ConstantConstraint(type, STATIC.mask);
   }
   
   public boolean isInstance() {
      return true;
   }
   
   public boolean isStatic() {
      return false;
   }
   
   public abstract Type getType(Scope scope);
}