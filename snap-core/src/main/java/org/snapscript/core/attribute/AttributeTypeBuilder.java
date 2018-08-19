package org.snapscript.core.attribute;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;

public class AttributeTypeBuilder {

   private final Attribute attribute;

   public AttributeTypeBuilder(Attribute attribute) {
      this.attribute = attribute;
   }

   public AttributeType create(Scope scope) {
      Constraint returns = attribute.getConstraint();
      List<Constraint> generics = attribute.getGenerics();
      List<Constraint> constraints = returns.getGenerics(scope);
      String name = returns.getName(scope);
      int require = constraints.size();
      int optional = generics.size();

      if(name != null || optional + require > 0) {
         return new GenericAttributeType(attribute);
      }
      return new StaticAttributeType(attribute);
   }
}
