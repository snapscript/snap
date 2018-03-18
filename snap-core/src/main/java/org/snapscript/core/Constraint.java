package org.snapscript.core;

import static org.snapscript.core.ConstraintType.INSTANCE;
import static org.snapscript.core.ConstraintType.MODULE;
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
   
   public static Constraint getModule(Module module) {
      Type type = module.getType();
      return new ConstantConstraint(type, MODULE.mask);
   }

   @Bug("this is not good")
   public static Constraint getInstance(Scope scope, Class require) {
      Module module = scope.getModule();
      Type type = module.getType(require);
      
      return new ConstantConstraint(type, INSTANCE.mask);
   }
   
   @Bug("this is not good")
   public static Constraint getInstance(Scope scope, Object value) {
      if(value != null) {
         Class require = value.getClass();
         Module module = scope.getModule();
         Type type = module.getType(require);
         
         return new ConstantConstraint(type, INSTANCE.mask);
      }
      return Constraint.getNone();
   }
   
   public boolean isInstance() {
      return true;
   }
   
   public boolean isStatic() {
      return false;
   }
   
   public boolean isModule() {
      return false;
   }   
   
   public abstract Type getType(Scope scope);
}