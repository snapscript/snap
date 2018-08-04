package org.snapscript.core.variable;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.Entity;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;

public class Transient extends Value {
   
   private final Constraint constraint;
   private final Entity source;
   private final Data value;
   
   public Transient(Object object, Entity source) {
      this(object, source, NONE);
   }
   
   public Transient(Object object, Entity source, Constraint constraint) {
      if(source == null){
         throw new IllegalStateException();
      }
      this.value = new ValueData(object, source);
      this.constraint = constraint;
      this.source = source;
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
   public Constraint getConstraint(){
      return constraint;
   }
   
   @Override
   public <T> T getValue(){
      return value.getValue();
   }
   
   @Override
   public void setData(Data value){
      throw new InternalStateException("Illegal modification of transient");
   } 
   
   @Override
   public String toString() {
      return String.valueOf(value);
   }
}