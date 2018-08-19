package org.snapscript.core.attribute;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;

public class StaticAttributeType implements AttributeType {
   
   private final Attribute attribute;

   public StaticAttributeType(Attribute attribute) {
      this.attribute = attribute;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      return attribute.getConstraint();
   }
}