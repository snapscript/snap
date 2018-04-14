package org.snapscript.core.variable;



public class ValueCache {

   private static final Value[][] CONSTANTS = new Value[5][2050];
   private static final int HIGH = 1024;
   private static final int LOW = -1024;
   private static final int BYTE = 0;
   private static final int SHORT = 1;
   private static final int INTEGER = 2;
   private static final int LONG = 3;
   private static final int CHARACTER = 4;
   
   static {
      for(int i = 0, j = LOW; j <= HIGH; j++, i++) {
         CONSTANTS[BYTE][i] = Value.getTransient((byte)j);
      }
      for(int i = 0, j = LOW; j <= HIGH; j++, i++) {
         CONSTANTS[SHORT][i] = Value.getTransient((short)j);
      }
      for(int i = 0, j = LOW; j <= HIGH; j++, i++) {
         CONSTANTS[INTEGER][i] = Value.getTransient(j);
      }
      for(int i = 0, j = LOW; j <= HIGH; j++, i++) {
         CONSTANTS[LONG][i] = Value.getTransient((long)j);
      }
      for(int i = 0, j = LOW; j <= HIGH; j++, i++) {
         CONSTANTS[CHARACTER][i] = Value.getTransient((char)j);
      }
   }
   
   public static Value getByte(int value) {
      if (value >= LOW && value <= HIGH) {
         return CONSTANTS[BYTE][value + -LOW];
      }
      return Value.getTransient((byte)value);
   }
   
   public static Value getShort(int value) {
      if (value >= LOW && value <= HIGH) {
         return CONSTANTS[SHORT][value + -LOW];
      }
      return Value.getTransient((short)value);
   }
   
   public static Value getInteger(int value) {
      if (value >= LOW && value <= HIGH) {
         return CONSTANTS[INTEGER][value + -LOW];
      }
      return Value.getTransient(value);
   }
   
   public static Value getLong(long value) {
      if (value >= LOW && value <= HIGH) {
         return CONSTANTS[LONG][(int)value + -LOW];
      }
      return Value.getTransient(value);
   }
   
   public static Value getCharacter(char value){
      if (value >= LOW && value <= HIGH) {
         return CONSTANTS[CHARACTER][value + -LOW];
      }
      return Value.getTransient(value);
   }
   
   public static Value getFloat(float value) {
      return Value.getTransient(value);
   }
   
   public static Value getDouble(double value) {
      return Value.getTransient(value);
   }
}