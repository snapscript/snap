package org.snapscript.core.variable;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Entity;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;

public class Blank extends Value {
   
   private Constraint constraint;
   private Entity source;
   private Data value;
   private int modifiers;
   
   public Blank(Object object, Entity source, Constraint constraint, int modifiers) {
      this.value = new ValueData(object, source);
      this.constraint = constraint;
      this.modifiers = modifiers;
      this.source = source;
   }
   
   @Override
   public boolean isConstant() {
      return value.getValue() != null;
   }
   
   @Override
   public boolean isProperty() {
      return modifiers != -1;
   }
   
   @Override
   public int getModifiers() {
      return modifiers;
   }
   
   @Override
   public Data getData() {
      return value;
   }   
   
   @Override
   public Entity getSource() {
      return source;
   }
   
   @Override
   public Constraint getConstraint() {
      return constraint;
   }
   
   @Override
   public <T> T getValue() {
      return value.getValue();
   }
   
   @Override
   public void setValue(Object value){
      if(this.value.getValue()!=null) {
         throw new InternalStateException("Illegal modification of constant");
      }
      this.value = new ValueData(value, source);
   } 
   
   @Override
   public String toString() {
      return String.valueOf(value);
   }
}