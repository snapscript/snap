package org.snapscript.core.variable;

import static org.snapscript.core.variable.Constant.BOOLEAN;
import static org.snapscript.core.variable.Constant.BYTE;
import static org.snapscript.core.variable.Constant.CHARACTER;
import static org.snapscript.core.variable.Constant.DOUBLE;
import static org.snapscript.core.variable.Constant.FLOAT;
import static org.snapscript.core.variable.Constant.INTEGER;
import static org.snapscript.core.variable.Constant.LONG;
import static org.snapscript.core.variable.Constant.SHORT;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;

public class ValueCache {

   private static final Value[][] CONSTANTS = new Value[5][2050];
   private static final int HIGH = 1024;
   private static final int LOW = -1024; 

//   static {
//      for(int i = 0, j = LOW; j <= HIGH; j++, i++) {
//         CONSTANTS[0][i] = Value.getTransient((byte)j, BYTE);
//      }
//      for(int i = 0, j = LOW; j <= HIGH; j++, i++) {
//         CONSTANTS[1][i] = Value.getTransient((short)j, SHORT);
//      }
//      for(int i = 0, j = LOW; j <= HIGH; j++, i++) {
//         CONSTANTS[2][i] = Value.getTransient(j, INTEGER);
//      }
//      for(int i = 0, j = LOW; j <= HIGH; j++, i++) {
//         CONSTANTS[3][i] = Value.getTransient((long)j, LONG);
//      }
//      for(int i = 0, j = LOW; j <= HIGH; j++, i++) {
//         CONSTANTS[4][i] = Value.getTransient((char)j, CHARACTER);
//      }
//   }
   
   public static Value getByte(Scope scope, int value) {
      Module module = scope.getModule();
      
//      if (value >= LOW && value <= HIGH) {
//         return CONSTANTS[0][value + -LOW];
//      }
      return Value.getTransient((byte)value, module, BYTE);
   }
   
   public static Value getShort(Scope scope, int value) {
      Module module = scope.getModule();
//      if (value >= LOW && value <= HIGH) {
//         return CONSTANTS[1][value + -LOW];
//      }
      return Value.getTransient((short)value, module, SHORT);
   }
   
   public static Value getInteger(Scope scope, int value) {
      Module module = scope.getModule();
//      if (value >= LOW && value <= HIGH) {
//         return CONSTANTS[2][value + -LOW];
//      }
      return Value.getTransient(value, module, INTEGER);
   }
   
   public static Value getLong(Scope scope, long value) {
      Module module = scope.getModule();
//      if (value >= LOW && value <= HIGH) {
//         return CONSTANTS[3][(int)value + -LOW];
//      }
      return Value.getTransient(value, module, LONG);
   }
   
   public static Value getCharacter(Scope scope, char value){
      Module module = scope.getModule();
//      if (value >= LOW && value <= HIGH) {
//         return CONSTANTS[4][value + -LOW];
//      }
      return Value.getTransient(value, module, CHARACTER);
   }
   
   public static Value getCharacter(Scope scope, int value){
      Module module = scope.getModule();
//      if (value >= LOW && value <= HIGH) {
//         return CONSTANTS[4][value + -LOW];
//      }
      return Value.getTransient((char)value, module, CHARACTER);
   }
   
   public static Value getFloat(Scope scope, float value) {
      Module module = scope.getModule();
      return Value.getTransient(value, module, FLOAT);
   }
   
   public static Value getDouble(Scope scope, double value) {
      Module module = scope.getModule();
      return Value.getTransient(value, module, DOUBLE);
   }
   
   public static Value getBoolean(Scope scope, boolean value) {
      Module module = scope.getModule();
      return Value.getTransient(value, module, BOOLEAN);
   }
}