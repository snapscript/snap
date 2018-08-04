package org.snapscript.core.variable;


public class DataMapper {
   
   public static Data toData(Value value) {
      return new ValueData(value.getValue(), value.getSource());
   }
}
