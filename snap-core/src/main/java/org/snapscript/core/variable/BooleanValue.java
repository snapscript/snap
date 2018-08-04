package org.snapscript.core.variable;

import org.snapscript.core.Entity;
import org.snapscript.core.error.InternalStateException;

public class BooleanValue extends Value {   

   private final Boolean value;
   private final Entity source;
   
   public BooleanValue(Boolean value, Entity source) {
      this.source = source;
      this.value = value;
   }
   
   @Override
   public Entity getSource() {
      return source;
   }
   
   @Override
   public Boolean getValue(){
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