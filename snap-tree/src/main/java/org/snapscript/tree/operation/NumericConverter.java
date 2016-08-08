package org.snapscript.tree.operation;

import org.snapscript.core.Value;
import org.snapscript.core.ValueType;

public enum NumericConverter {
   DOUBLE {
      @Override
      public Value convert(Number reference) {
         Double value = reference.doubleValue();
         return ValueType.getTransient(value);
      }
      @Override
      public Value increment(Number reference) {
         Double value = reference.doubleValue();
         return ValueType.getTransient(value + 1.0d);
      }
      @Override
      public Value decrement(Number reference) {
         Double value = reference.doubleValue();
         return ValueType.getTransient(value - 1.0d);
      }
   },
   LONG {
      @Override
      public Value convert(Number reference) {
         Long value = reference.longValue();
         return ValueType.getTransient(value);
      }
      @Override
      public Value increment(Number reference) {
         Long value = reference.longValue();
         return ValueType.getTransient(value + 1L);
      }
      @Override
      public Value decrement(Number reference) {
         Long value = reference.longValue();
         return ValueType.getTransient(value - 1L);
      }
   },
   FLOAT {
      @Override
      public Value convert(Number reference) {
         Float value = reference.floatValue();
         return ValueType.getTransient(value);
      }
      @Override
      public Value increment(Number reference) {
         Float value = reference.floatValue();
         return ValueType.getTransient(value + 1.0f);
      }
      @Override
      public Value decrement(Number reference) {
         Float value = reference.floatValue();
         return ValueType.getTransient(value - 1.0f);
      }
   },
   INTEGER {
      @Override
      public Value convert(Number reference) {
         Integer value = reference.intValue();
         return ValueType.getTransient(value);
      }
      @Override
      public Value increment(Number reference) {
         Integer value = reference.intValue();
         return ValueType.getTransient(value + 1);
      }
      @Override
      public Value decrement(Number reference) {
         Integer value = reference.intValue();
         return ValueType.getTransient(value - 1);
      }
   },
   SHORT {
      @Override
      public Value convert(Number reference) {
         Short value = reference.shortValue();
         return ValueType.getTransient(value);
      }
      @Override
      public Value increment(Number reference) {
         Short value = reference.shortValue();
         return ValueType.getTransient(value + 1);
      }
      @Override
      public Value decrement(Number reference) {
         Short value = reference.shortValue();
         return ValueType.getTransient(value - 1);
      }
   },
   BYTE {
      @Override
      public Value convert(Number reference) {
         Byte value = reference.byteValue();
         return ValueType.getTransient(value);
      }
      @Override
      public Value increment(Number reference) {
         Byte value = reference.byteValue();
         return ValueType.getTransient(value + 1);
      }
      @Override
      public Value decrement(Number reference) {
         Byte value = reference.byteValue();
         return ValueType.getTransient(value - 1);
      }
   };
   
   public abstract Value convert(Number value);
   public abstract Value increment(Number value);
   public abstract Value decrement(Number value);
   
   public static NumericConverter resolveConverter(Number value) {
      Class type = value.getClass();
      
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
      Class primary = left.getType();
      Class secondary = right.getType();

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
      return DOUBLE;
   }
}