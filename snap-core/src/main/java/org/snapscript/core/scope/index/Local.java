package org.snapscript.core.scope.index;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.variable.Value;

public abstract class Local extends Value {
   
   public static Local getConstant(Object value, String name) {
      return new LocalConstant(value, name, null);
   }
   
   public static Local getConstant(Object value, String name, Constraint type) {
      return new LocalConstant(value, name, type);
   }

   public static Local getReference(Object value, String name) {
      return new LocalReference(value, name, null);
   }
   
   public static Local getReference(Object value, String name, Constraint type) {
      return new LocalReference(value, name, type);
   }
}