package org.snapscript.core.attribute;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class StaticAttributeResult implements AttributeResult {
   
   private final Attribute attribute;

   public StaticAttributeResult(Attribute attribute) {
      this.attribute = attribute;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left, Type... types) throws Exception {
      return attribute.getConstraint();
   }
}