package org.snapscript.core.variable;

import org.snapscript.core.scope.Scope;

public class ValueCache {
   
   public static Value getByte(Scope scope, int value) {
      return scope.getModule().getContext().getTable().getByte(value);
   }
   
   public static Value getShort(Scope scope, int value) {
      return scope.getModule().getContext().getTable().getShort(value);
   }
   
   public static Value getInteger(Scope scope, int value) {
      return scope.getModule().getContext().getTable().getInteger(value);
   }
   
   public static Value getLong(Scope scope, long value) {
      return scope.getModule().getContext().getTable().getLong(value);
   }
   
   public static Value getCharacter(Scope scope, char value){
      return scope.getModule().getContext().getTable().getCharacter(value);
   }
   
   public static Value getCharacter(Scope scope, int value){
      return scope.getModule().getContext().getTable().getCharacter(value);
   }
   
   public static Value getFloat(Scope scope, float value) {
      return scope.getModule().getContext().getTable().getFloat(value);
   }
   
   public static Value getDouble(Scope scope, double value) {
      return scope.getModule().getContext().getTable().getDouble(value);
   }
   
   public static Value getBoolean(Scope scope, boolean value) {
      return scope.getModule().getContext().getTable().getBoolean(value);
   }
}