package org.snapscript.core.variable;

import org.snapscript.core.Entity;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.type.Type;

public class PrimitiveValue extends Value {   

   private final Constraint constraint;
   private final Object value;
   private final Type type;
   
   public PrimitiveValue(Object value, Type type, Constraint constraint) {
      this.constraint = constraint;
      this.value = value;
      this.type = type;
   }
   
   @Override
   public Type getType() {
      return type;
   }
   
   @Override
   public Entity getSource() {
      return type;
   }
   
   @Override
   public Constraint getConstraint() {
      return constraint;
   }
   
   @Override
   public Object getValue(){
      return value;
   }
   
   @Override
   public void setValue(Object value){
      throw new InternalStateException("Illegal modification of value");
   }
   
   @Override
   public String toString() {
      return String.valueOf(value);
   }
}