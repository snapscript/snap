package org.snapscript.core.variable;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.Entity;
import org.snapscript.core.constraint.Constraint;

public class Reference extends Value {
   
   private Constraint constraint;
   private Entity source;
   private Object value;
   private int modifiers;
   
   public Reference(Object value, Entity source) {
      this(value, source, NONE);
   }
   
   public Reference(Object value, Entity source, Constraint constraint) {
      this(value, source, constraint, -1);
   }
   
   public Reference(Object value, Entity source, Constraint constraint, int modifiers) {
      if(source == null){
         throw new IllegalStateException();
      }
      this.constraint = constraint; 
      this.modifiers = modifiers;
      this.source = source;
      this.value = value;
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
   public Entity getSource(){
      return source;
   }
   
   @Override
   public Constraint getConstraint() {
      return constraint;
   }
   
   @Override
   public <T> T getValue() {
      return (T)value;
   }

   @Override
   public void setValue(Object value) {
      this.value = value;
   }
   
   @Override
   public String toString() {
      return String.valueOf(value);
   }
}