package org.snapscript.tree.collection;

import java.util.List;
import java.util.Map;

import org.snapscript.core.InternalArgumentException;
import org.snapscript.core.array.ArrayBuilder;

public class CollectionConverter {

   private final ArrayBuilder builder;
   
   public CollectionConverter() {
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
         if(Map.class.isAssignableFrom(type)) {
            return true;
         }
      }
      return false;
   }
   
   public Object convert(Object value) throws Exception {
      if(value != null) {
         Class type = value.getClass();
         
         if(type.isArray()) {
            return builder.convert(value);
         }
         if(List.class.isAssignableFrom(type)) {
            return value;
         }
         if(Map.class.isAssignableFrom(type)) {
            return value;
         }
         throw new InternalArgumentException("Illegal index of " + type);
      }
      return null;
   }
}