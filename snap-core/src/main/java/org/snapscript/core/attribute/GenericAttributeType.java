package org.snapscript.core.attribute;

import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.transform.ConstraintRule;
import org.snapscript.core.constraint.transform.ConstraintTransform;
import org.snapscript.core.constraint.transform.ConstraintTransformer;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class GenericAttributeType implements AttributeType {

   private final Attribute attribute;

   public GenericAttributeType(Attribute attribute) {
      this.attribute = attribute;      
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      Module module = scope.getModule();
      Context context = module.getContext();  
      Type constraint = left.getType(scope);
      Constraint returns = attribute.getConstraint();       
      ConstraintTransformer transformer = context.getTransformer();
      ConstraintTransform transform = transformer.transform(constraint, attribute);
      ConstraintRule rule = transform.apply(left);

      return rule.getResult(scope, returns);
   }
}