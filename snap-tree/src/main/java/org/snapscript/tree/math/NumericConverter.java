package org.snapscript.tree.math;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.ValueCache;

public enum NumericConverter {
   DOUBLE {
      @Override
      public Value convert(Scope scope, Number number) {
         double value = number.doubleValue();
         return ValueCache.getDouble(scope, value);
      }
      @Override
      public Value increment(Scope scope, Number number) {
         double value = number.doubleValue();
         return ValueCache.getDouble(scope, value + 1.0d);
      }
      @Override
      public Value decrement(Scope scope, Number number) {
         double value = number.doubleValue();
         return ValueCache.getDouble(scope, value - 1.0d);
      }
   },
   LONG {
      @Override
      public Value convert(Scope scope, Number number) {
         long value = number.longValue();
         return ValueCache.getLong(scope, value);
      }
      @Override
      public Value increment(Scope scope, Number number) {
         long value = number.longValue();
         return ValueCache.getLong(scope, value + 1L);
      }
      @Override
      public Value decrement(Scope scope, Number number) {
         long value = number.longValue();
         return ValueCache.getLong(scope, value - 1L);
      }
   },
   FLOAT {
      @Override
      public Value convert(Scope scope, Number number) {
         float value = number.floatValue();
         return ValueCache.getFloat(scope, value);
      }
      @Override
      public Value increment(Scope scope, Number number) {
         float value = number.floatValue();
         return ValueCache.getFloat(scope, value + 1.0f);
      }
      @Override
      public Value decrement(Scope scope, Number number) {
         float value = number.floatValue();
         return ValueCache.getFloat(scope, value - 1.0f);
      }
   },
   INTEGER {
      @Override
      public Value convert(Scope scope, Number number) {
         int value = number.intValue();
         return ValueCache.getInteger(scope, value);
      }
      @Override
      public Value increment(Scope scope, Number number) {
         int value = number.intValue();
         return ValueCache.getInteger(scope, value + 1);
      }
      @Override
      public Value decrement(Scope scope, Number number) {
         int value = number.intValue();
         return ValueCache.getInteger(scope, value - 1);
      }
   },
   SHORT {
      @Override
      public Value convert(Scope scope, Number number) {
         short value = number.shortValue();
         return ValueCache.getShort(scope, value);
      }
      @Override
      public Value increment(Scope scope, Number number) {
         short value = number.shortValue();
         return ValueCache.getShort(scope, value + 1);
      }
      @Override
      public Value decrement(Scope scope, Number number) {
         short value = number.shortValue();
         return ValueCache.getShort(scope, value - 1);
      }
   },
   BYTE {
      @Override
      public Value convert(Scope scope, Number number) {
         byte value = number.byteValue();
         return ValueCache.getByte(scope, value);
      }
      @Override
      public Value increment(Scope scope, Number number) {
         byte value = number.byteValue();
         return ValueCache.getByte(scope, value + 1);
      }
      @Override
      public Value decrement(Scope scope, Number number) {
         byte value = number.byteValue();
         return ValueCache.getByte(scope, value - 1);
      }
   };
   
   public abstract Value convert(Scope scope, Number number);
   public abstract Value increment(Scope scope, Number number);
   public abstract Value decrement(Scope scope, Number number);
   
   public static NumericConverter resolveConverter(Number number) {
      Class type = number.getClass();
      
      if (Double.class == type) {
         return DOUBLE;
      }
      if (Long.class == type) {
         return LONG;
      }
      if (Float.class == type) {
         return FLOAT;
      }
      if (Integer.class == type) {
         return INTEGER;
      }
      if (Short.class == type) {
         return SHORT;
      }
      if (Byte.class == type) {
         return BYTE;
      }
      return DOUBLE;
   }
   
   public static NumericConverter resolveConverter(Value left, Value right) {
      Class primary = left.getType().getType();
      Class secondary = right.getType().getType();

      if (Double.class == primary || Double.class == secondary) {
         return DOUBLE;
      }
      if (Long.class == primary || Long.class == secondary) {
         return LONG;
      }
      if (Float.class == primary || Float.class == secondary) {
         return FLOAT;
      }
      if (Integer.class == primary || Integer.class == secondary) {
         return INTEGER;
      }
      if (Short.class == primary || Short.class == secondary) {
         return SHORT;
      }
      if (Byte.class == primary || Byte.class == secondary) {
         return BYTE;
      }      
      if (Character.class == primary || Character.class == secondary) {
         return INTEGER;
      }
      return DOUBLE;
   }
}