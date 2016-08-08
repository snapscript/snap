package org.snapscript.tree.collection;

import java.util.List;

import org.snapscript.core.InternalArgumentException;

public class ArrayListConverter {

   private final ArrayBuilder builder;
   
   public ArrayListConverter() {
      this.builder = new ArrayBuilder();
   }
   
   public boolean accept(Object value) throws Exception {
      if(value != null) {
         Class type = value.getClass();
         
         if(type.isArray()) {
            return true;
         }
         if(List.class.isAssignableFrom(type)) {
            return true;
         }
      }
      return false;
   }
   
   public List convert(Object value) throws Exception {
      if(value != null) {
         Class type = value.getClass();
         
         if(type.isArray()) {
            return builder.convert(value);
         }
         if(List.class.isAssignableFrom(type)) {
            return (List)value;
         }
         throw new InternalArgumentException("The " + type + " is not a list");
      }
      return null;
   }
}
