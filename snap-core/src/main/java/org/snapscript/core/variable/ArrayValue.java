package org.snapscript.core.variable;

import java.lang.reflect.Array;

import org.snapscript.core.Entity;

public class ArrayValue extends Value {
   
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
   public Object getValue(){
      return Array.get(array, index);
   }
   
   @Override
   public void setValue(Object value){
      Array.set(array, index, value);
   }       
   
   @Override
   public String toString() {
      return String.valueOf(array);
   }
}