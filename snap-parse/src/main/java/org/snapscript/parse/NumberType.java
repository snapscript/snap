package org.snapscript.parse;

public enum NumberType {
   INTEGER {
      @Override
      public Number convert(Number number) {
         return number.intValue();
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