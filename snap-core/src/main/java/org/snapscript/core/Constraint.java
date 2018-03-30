package org.snapscript.core;

import static org.snapscript.core.ConstraintType.INSTANCE;
import static org.snapscript.core.ConstraintType.STATIC;

public abstract class Constraint {

   public static Constraint getNone() {
      return new ConstantConstraint(null);
   }
   
   public static Constraint getInstance(Type type) {
      if(type!=null&& type.getType()!=null){
         Class t=type.getType();
         if(t==Object.class || t==void.class||t==Void.class){
            return Constraint.getNone();
         }
      }
      return new ConstantConstraint(type, INSTANCE.mask);
   }
   
   public static Constraint getStatic(Type type) {
      if(type!=null&& type.getType()!=null){
         Class t=type.getType();
         if(t==Object.class || t==void.class||t==Void.class){
            return Constraint.getNone();
         }
      }
      return new ConstantConstraint(type, STATIC.mask);
   }
   
   public static Constraint getModule(Module module) {
      return new ModuleConstraint(module);
   }

   @Bug("this is not good")
   public static Constraint getInstance(Class t) {
      if(t!=null){
         if(t==Object.class || t==void.class||t==Void.class){
            return Constraint.getNone();
         }
      }
      return new ClassConstraint(t);
   }
   
   @Bug("this is not good")
   public static Constraint getInstance(Object value) {
      if(value != null) {
         Class require = value.getClass();
         return new ClassConstraint(require);
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
   
   private static class ClassConstraint extends Constraint {
     
      private Class require;
      private Type type;
      
      public ClassConstraint(Class require) {
         this.require = require;
      }
      
      public Type getType(Scope scope){
         if(type == null) {
            Module module = scope.getModule();
            type = module.getType(require);
         }
         return type;         
      }
   }
   
   private static class ModuleConstraint extends Constraint {
      
      private Module module;
      
      public ModuleConstraint(Module module) {
         this.module = module;
      }
      
      public Type getType(Scope scope){
         return module.getType();
      }
      
      public boolean isModule() {
         return true;
      }
   }
}