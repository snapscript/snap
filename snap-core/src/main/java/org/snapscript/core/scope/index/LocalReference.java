package org.snapscript.core.scope.index;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.Entity;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.variable.Data;
import org.snapscript.core.variable.DataMapper;
import org.snapscript.core.variable.ValueData;

public class LocalReference extends Local {

   private Constraint constraint;
   private Entity source;
   private Data value;
   private String name;
   
   public LocalReference(Object object, Entity source, String name) {
      this(object, source, name, NONE);
   }
   
   public LocalReference(Object object, Entity source, String name, Constraint constraint) {
      if(source == null){
         throw new IllegalStateException();
      }
      this.value = new ValueData(object, source);
      this.constraint = constraint; 
      this.source = source;
      this.name = name;
   }
   
   @Override
   public Entity getSource() {
      return source;
   }
   
   @Override
   public Data getData() {
      return value;
   }   
   
   @Override
   public Constraint getConstraint() {
      return constraint;
   }
   
   @Override
   public String getName() {
      return name;
   }
   
   @Override
   public <T> T getValue() {
      return value.getValue();
   }

   @Override
   public void setValue(Object value) {
      this.value = new ValueData(value, source);
   }
   
   @Override
   public String toString() {
      return String.format("%s: %s", name, value);
   }
}