package org.snapscript.core.property;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.constraint.Constraint;

public class PropertyValue extends Value {

   private final Property property;
   private final Object object;   
   private final String name;

   public PropertyValue(Property property, Object object, String name) {
      this.property = property;
      this.object = object;
      this.name = name;
   }
   
   @Override
   public boolean isProperty() {
      return true;
   }
   
   @Override
   public Type getType(Scope scope) {
      Constraint constraint = property.getConstraint();      
      
      if(constraint != null) {
         return constraint.getType(scope);
      }
      return null;
   }
   
   @Override
   public int getModifiers() {
      return property.getModifiers();
   }

   public String getName(){
      return name;
   }
   
   @Override
   public <T> T getValue() {
      return (T)property.getValue(object);
   }

   @Override
   public void setValue(Object value) {
      property.setValue(object, value);
   }
   
   @Override
   public String toString() {
      return String.valueOf(object);
   }
}