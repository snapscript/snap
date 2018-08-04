package org.snapscript.core.variable;

import static org.snapscript.core.variable.Constant.BOOLEAN;
import static org.snapscript.core.variable.Constant.BYTE;
import static org.snapscript.core.variable.Constant.CHARACTER;
import static org.snapscript.core.variable.Constant.DOUBLE;
import static org.snapscript.core.variable.Constant.FLOAT;
import static org.snapscript.core.variable.Constant.INTEGER;
import static org.snapscript.core.variable.Constant.LONG;
import static org.snapscript.core.variable.Constant.SHORT;
import static org.snapscript.core.variable.Constant.STRING;

import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeLoader;

public class ConstantTable {   

   private static final Value[][] CONSTANTS = new Value[5][2050];
   private static final int HIGH = 1024;
   private static final int LOW = -1024; 
   
   private static final int BYTE_TYPE = 0;
   private static final int SHORT_TYPE = 1;
   private static final int INTEGER_TYPE = 2;
   private static final int LONG_TYPE = 3;
   private static final int CHARACTER_TYPE = 4;
   
   private static final int BOOLEAN_TYPE = 5;
   private static final int FLOAT_TYPE = 6;
   private static final int DOUBLE_TYPE = 7;
   private static final int STRING_TYPE = 8;
   
   private ConstantCache cache;
   private TypeLoader loader;
   
   public ConstantTable(TypeLoader loader) {
      this.loader = loader;
   }   

   private ConstantCache getCache() {
      return getCache(HIGH, LOW);
   }
   
   private ConstantCache getCache(int high, int low) {
      if(cache == null) {
         PrimitiveValue[][] constants = new PrimitiveValue[5][2050];
         PrimitiveValue[] bools = new PrimitiveValue[2];
         Type[] types = new Type[9]; 
   
         types[BOOLEAN_TYPE] = loader.loadType(Boolean.class);
         types[BYTE_TYPE] = loader.loadType(Byte.class);
         types[SHORT_TYPE] = loader.loadType(Short.class);
         types[INTEGER_TYPE] = loader.loadType(Integer.class);
         types[LONG_TYPE] = loader.loadType(Long.class);
         types[CHARACTER_TYPE] = loader.loadType(Character.class);
         types[DOUBLE_TYPE] = loader.loadType(Double.class);
         types[FLOAT_TYPE] = loader.loadType(Float.class);
         types[STRING_TYPE] = loader.loadType(String.class);
         
         for(int i = 0, j = low; j <= high; j++, i++) {
            constants[BYTE_TYPE][i] = new PrimitiveValue((byte)j, types[BYTE_TYPE], BYTE);
         }
         for(int i = 0, j = low; j <= high; j++, i++) {
            constants[SHORT_TYPE][i] = new PrimitiveValue((short)j, types[SHORT_TYPE], SHORT);
         }
         for(int i = 0, j = LOW; j <= high; j++, i++) {
            constants[INTEGER_TYPE][i] = new PrimitiveValue(j, types[INTEGER_TYPE], INTEGER);
         }
         for(int i = 0, j = low; j <= high; j++, i++) {
            constants[LONG_TYPE][i] = new PrimitiveValue((long)j, types[LONG_TYPE], LONG);
         }
         for(int i = 0, j = low; j <= high; j++, i++) {
            constants[CHARACTER_TYPE][i] = new PrimitiveValue((char)j, types[CHARACTER_TYPE], CHARACTER);
         }
         bools[0] = new PrimitiveValue(false, types[BOOLEAN_TYPE], BOOLEAN);
         bools[1] = new PrimitiveValue(true, types[BOOLEAN_TYPE], BOOLEAN);
         
         cache = new ConstantCache(constants, bools, types, high, low);
      }
      return cache;
   }
   
   public PrimitiveValue getByte(int value) {
      return getCache().getByte(value);
   }
   
   public PrimitiveValue getShort(int value) {
      return getCache().getShort(value);
   }
   
   public PrimitiveValue getInteger(int value) {
      return getCache().getInteger(value);
   }
   
   public PrimitiveValue getLong(long value) {
      return getCache().getLong(value);
   }
   
   public PrimitiveValue getCharacter(char value){
      return getCache().getCharacter(value);
   }
   
   public PrimitiveValue getCharacter(int value){
      return getCache().getCharacter(value);
   }
   
   public PrimitiveValue getFloat(float value) {
      return getCache().getFloat(value);
   }
   
   public PrimitiveValue getDouble(double value) {
      return getCache().getDouble(value);
   }
   
   public PrimitiveValue getBoolean(boolean value) {
      return getCache().getBoolean(value);
   }
   
   public PrimitiveValue getString(String value) {
      return getCache().getString(value);
   }
   
   private static class ConstantCache {
      
      private final PrimitiveValue[][] constants;
      private final PrimitiveValue[] flags;
      private final Type[] types;      
      private final int high;
      private final int low;

      public ConstantCache(PrimitiveValue[][] constants, PrimitiveValue[] flags, Type[] types, int high, int low) {
         this.constants = constants;
         this.flags = flags;
         this.types = types;
         this.high = high;
         this.low = low;
      }
      
      public PrimitiveValue getBoolean(boolean value) {
         return flags[value ? 1 : 0];
      }
      
      public PrimitiveValue getByte(int value) {
          if (value >= low && value <= high) {
             return constants[BYTE_TYPE][value + -low];
          }
         return new PrimitiveValue((byte)value, types[BYTE_TYPE], BYTE);
      }
      
      public PrimitiveValue getShort(int value) {
         if (value >= low && value <= high) {
            return constants[SHORT_TYPE][value + -low];
         }
        return new PrimitiveValue((short)value, types[SHORT_TYPE], SHORT);
      }
      
      public PrimitiveValue getInteger(int value) {
         if (value >= low && value <= high) {
            return constants[INTEGER_TYPE][value + -low];
         }
        return new PrimitiveValue(value, types[INTEGER_TYPE], INTEGER);
      }
      
      public PrimitiveValue getLong(long value) {
         if (value >= low && value <= high) {
            return constants[LONG_TYPE][(int)value + -low];
         }
        return new PrimitiveValue(value, types[LONG_TYPE], LONG);
      }
      
      public PrimitiveValue getCharacter(char value){
         if (value >= low && value <= high) {
            return constants[CHARACTER_TYPE][value + -low];
         }
        return new PrimitiveValue((char)value, types[CHARACTER_TYPE], CHARACTER);
      }
      
      public PrimitiveValue getCharacter(int value){
         if (value >= low && value <= high) {
            return constants[CHARACTER_TYPE][value + -low];
         }
        return new PrimitiveValue((char)value, types[CHARACTER_TYPE], CHARACTER);
      }
      
      public PrimitiveValue getFloat(float value) {
         return new PrimitiveValue(value, types[FLOAT_TYPE], FLOAT);
      }
      
      public PrimitiveValue getDouble(double value) {
         return new PrimitiveValue(value, types[DOUBLE_TYPE], DOUBLE);
      }
      
      public PrimitiveValue getString(String value) {
         return new PrimitiveValue(value, types[STRING_TYPE], STRING);
      }
   }
   

}