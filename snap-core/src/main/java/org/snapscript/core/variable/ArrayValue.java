package org.snapscript.core.variable;

import java.lang.reflect.Array;

import org.snapscript.core.Bug;
import org.snapscript.core.Entity;
import org.snapscript.core.type.Type;

public class ArrayValue extends DataValue {
   
   private final Entity source;
   private final Object array;
   private final Integer index;
   
   public ArrayValue(Object array, Entity source, Integer index) {      
      this.source = source;
      this.array = array;
      this.index = index;
   }
   
   @Override
   public Entity getSource() {
      return source;
   }
   
   @Override
   public Type getType() {         
      return source.getScope().getModule().getContext().getExtractor().getType(getValue());
   }

   @Override
   public Data getData() {
      return this;
   }   
   
   @Override
   public Object getValue(){
      return Array.get(array, index);
   }
   
   @Bug
   @Override
   public void setData(Data value){
      Array.set(array, index, value.getValue());
   }       
   
   @Override
   public String toString() {
      return String.valueOf(array);
   }
}