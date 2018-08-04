package org.snapscript.core.variable;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.Entity;
import org.snapscript.core.constraint.Constraint;

public class Reference extends Value {
   
   private Constraint constraint;
   private Entity source;
   private Data value;
   private int modifiers;
   
   public Reference(Object object, Entity source) {
      this(object, source, NONE);
   }
   
   public Reference(Object object, Entity source, Constraint constraint) {
      this(object, source, constraint, -1);
   }
   
   public Reference(Object object, Entity source, Constraint constraint, int modifiers) {
      if(source == null){
         throw new IllegalStateException();
      }
      this.value = new ValueData(object, source);
      this.constraint = constraint; 
      this.modifiers = modifiers;
      this.source = source;
   }
   
   @Override
   public boolean isProperty(){
      return modifiers != -1;
   }
   
   @Override
   public int getModifiers(){
      return modifiers;
   }
   
   @Override
   public Data getData() {
      return value;
   }   
   
   @Override
   public Entity getSource(){
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
   public void setData(Data value) {
      this.value = value;
   }
   
   @Override
   public String toString() {
      return String.valueOf(value);
   }
}