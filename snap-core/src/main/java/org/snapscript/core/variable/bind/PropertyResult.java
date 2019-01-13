package org.snapscript.core.variable.bind;

import static org.snapscript.core.scope.index.AddressType.INSTANCE;
import static org.snapscript.core.scope.index.AddressType.STATIC;

import org.snapscript.core.Entity;
import org.snapscript.core.ModifierType;
import org.snapscript.core.attribute.AttributeResult;
import org.snapscript.core.attribute.AttributeResultBinder;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Address;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class PropertyResult implements VariableResult {
   
   private final AttributeResultBinder binder;
   private final Property property;  
   private final Entity entity;
   private final String name;
   private final Type[] empty;
   
   public PropertyResult(Property property, Entity entity, String name){
      this.binder = new AttributeResultBinder(property);
      this.empty = new Type[]{};
      this.property = property;
      this.entity = entity;
      this.name = name;
   }
   
   @Override
   public Address getAddress(int offset) {
      String alias = property.getAlias();
      int modifiers = property.getModifiers();
      
      if(!ModifierType.isStatic(modifiers)) {
         return INSTANCE.getAddress(alias, offset);
      }
      return STATIC.getAddress(alias, offset);
   }
   
   @Override
   public Constraint getConstraint(Constraint left) {
      Scope scope = entity.getScope();
      AttributeResult result = binder.bind(scope);

      if(result == null) {
         throw new InternalStateException("No type for '" + property + "'");
      }
      try {
         return result.getConstraint(scope, left, empty);
      } catch(Exception e) {
         throw new InternalStateException("Invalid constraint for '" + property + "'", e);
      }
   }
   
   @Override
   public Value getValue(Object left) {
      return new PropertyValue(property, left, name);
   }
}
