package org.snapscript.core.variable;

import org.snapscript.core.Entity;
import org.snapscript.core.type.Type;

public class ValueData implements Data {
   
   private final Object value;
   private Entity source;
   private Type type;
   
   public ValueData(Object value, Entity source) {
      this.source = source;
      this.value = value;
   }

   @Override
   public Type getType() {
      if(type == null && value != null) {            
         type = source.getScope().getModule().getContext().getExtractor().getType(value);
         
      }
      return type;
   }

   @Override
   public <T> T getValue() {
      return (T)value;
   }
   
   @Override
   public String toString() {
      return String.valueOf(value);
   }
}
