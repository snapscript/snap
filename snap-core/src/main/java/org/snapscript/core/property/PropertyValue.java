package org.snapscript.core.property;

import org.snapscript.core.Bug;
import org.snapscript.core.Entity;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Data;
import org.snapscript.core.variable.DataValue;

public class PropertyValue extends DataValue {

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
   public Entity getSource() {
      return property.getSource();
   }
   
   @Override
   public Type getType() {         
      return property.getSource().getScope().getModule().getContext().getExtractor().getType(getValue());
   }

   @Override
   public Data getData() {
      return this;
   }  
   
   @Override
   public Constraint getConstraint() {
      return property.getConstraint();
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

   @Bug
   @Override
   public void setData(Data value) {
      Object x = value.getValue();
      property.setValue(object, x);
   }
   
   @Override
   public String toString() {
      return String.valueOf(object);
   }
}