package org.snapscript.core.attribute;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;

public class StaticAttributeType implements AttributeType {
   
   private final Constraint constraint;

   public StaticAttributeType(Constraint constraint) {
      this.constraint = constraint;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      return constraint;
   }
}