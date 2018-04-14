package org.snapscript.parse;

public enum NumberType {
   INTEGER {
      @Override
      public Number convert(Number number) {
         long value = number.longValue();
         
         if(value > 0 && value <= Integer.MAX_VALUE) {
            return number.intValue();
         }
         if(value < 0 && value >= Integer.MIN_VALUE) {
            return number.intValue();
         }
         return value; // promote if too big
      }         
   },
   DOUBLE {
      @Override
      public Number convert(Number number) {
         return number.doubleValue();
      }
   },
   FLOAT {
      @Override
      public Number convert(Number number) {
         return number.floatValue();
      }
   },
   LONG {
      @Override
      public Number convert(Number number) {
         return number.longValue();
      }
   },
   SHORT {
      @Override
      public Number convert(Number number) {
         return number.shortValue();
      }
   },
   BYTE {
      @Override
      public Number convert(Number number) {
         return number.byteValue();
      }
   };
   
   public abstract Number convert(Number number);
}