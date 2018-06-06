package org.snapscript.core.variable.bind;

import org.snapscript.core.Entity;
import org.snapscript.core.attribute.AttributeType;
import org.snapscript.core.attribute.AttributeTypeBinder;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public class PropertyResult implements VariableResult {
   
   private final AttributeTypeBinder binder;
   private final Property property;  
   private final Entity entity;
   private final String name;
   
   public PropertyResult(Property property, Entity entity, String name){
      this.binder = new AttributeTypeBinder(property);
      this.property = property;
      this.entity = entity;
      this.name = name;
   }
   
   public Constraint getConstraint(Constraint left) {
      Scope scope = entity.getScope();
      AttributeType type = binder.bind(scope);
      
      if(type == null) {
         throw new InternalStateException("No type for '" + property + "'");
      }
      return type.getConstraint(scope, left);  
   }
   
   public Value getValue(Object left) {
      return new PropertyValue(property, left, name);
   }
}
