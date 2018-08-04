package org.snapscript.core.variable;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.Entity;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;

public class Transient extends Value {
   
   private final Constraint constraint;
   private final Entity source;
   private final Object object;
   
   public Transient(Object object, Entity source) {
      this(object, source, NONE);
   }
   
   public Transient(Object object, Entity source, Constraint constraint) {
      if(source == null){
         throw new IllegalStateException();
      }
      this.constraint = constraint;
      this.source = source;
      this.object = object;
   }
   
   @Override
   public Entity getSource() {
      return source;
   }
   
   @Override
   public Constraint getConstraint(){
      return constraint;
   }
   
   @Override
   public <T> T getValue(){
      return (T)object;
   }
   
   @Override
   public void setValue(Object value){
      throw new InternalStateException("Illegal modification of transient");
   } 
   
   @Override
   public String toString() {
      return String.valueOf(object);
   }
}