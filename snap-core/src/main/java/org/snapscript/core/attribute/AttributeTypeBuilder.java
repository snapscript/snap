package org.snapscript.core.attribute;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class AttributeTypeBuilder {
   
   private final Attribute attribute;
   
   public AttributeTypeBuilder(Attribute attribute) {
      this.attribute = attribute;
   }
   
   public AttributeType create(Scope scope) {
      Constraint returns = attribute.getConstraint();
      List<Constraint> constraints = returns.getGenerics(scope);
      String name = returns.getName(scope);
      Type declared = attribute.getType();
      int count = constraints.size();
      
      if(name != null || count > 0) {
         return new GenericAttributeType(returns, declared);
      }
      return new StaticAttributeType(returns);
   }

}
