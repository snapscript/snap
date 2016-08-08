package org.snapscript.parse;

public class NumberConverter {

   public Number convert(Class type, Number value) {
      if(type == int.class) {
         return value.intValue();
      }
      if(type == long.class) {
         return value.longValue();
      } 
      if(type == float.class) {
         return value.floatValue();
      }
      if(type == byte.class) {
         return value.byteValue();
      }
      if(type == double.class){
         return value.doubleValue();
      }
      if(type == short.class) {
         return value.shortValue();
      }
      throw new ParseException("No number conversion for " + type);
   }
}
