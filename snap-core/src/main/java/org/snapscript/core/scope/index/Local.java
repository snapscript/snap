package org.snapscript.core.scope.index;

import org.snapscript.core.Entity;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.variable.Value;

public abstract class Local extends Value {
   
   public static Local getConstant(Object value, Entity source, String name) {
      return new LocalConstant(value, source, name, null);
   }
   
   public static Local getConstant(Object value, Entity source, String name, Constraint type) {
      return new LocalConstant(value, source, name, type);
   }

   public static Local getReference(Object value, Entity source, String name) {
      return new LocalReference(value, source, name, null);
   }
   
   public static Local getReference(Object value, Entity source, String name, Constraint type) {
      return new LocalReference(value, source, name, type);
   }
}