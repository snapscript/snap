package org.snapscript.core.variable;

import org.snapscript.core.scope.Scope;

public class ValueCache {
   
   public static PrimitiveValue getByte(Scope scope, int value) {
      return scope.getModule().getContext().getTable().getByte(value);
   }
   
   public static PrimitiveValue getShort(Scope scope, int value) {
      return scope.getModule().getContext().getTable().getShort(value);
   }
   
   public static PrimitiveValue getInteger(Scope scope, int value) {
      return scope.getModule().getContext().getTable().getInteger(value);
   }
   
   public static PrimitiveValue getLong(Scope scope, long value) {
      return scope.getModule().getContext().getTable().getLong(value);
   }
   
   public static PrimitiveValue getCharacter(Scope scope, char value){
      return scope.getModule().getContext().getTable().getCharacter(value);
   }
   
   public static PrimitiveValue getCharacter(Scope scope, int value){
      return scope.getModule().getContext().getTable().getCharacter(value);
   }
   
   public static PrimitiveValue getFloat(Scope scope, float value) {
      return scope.getModule().getContext().getTable().getFloat(value);
   }
   
   public static PrimitiveValue getDouble(Scope scope, double value) {
      return scope.getModule().getContext().getTable().getDouble(value);
   }
   
   public static PrimitiveValue getBoolean(Scope scope, boolean value) {
      return scope.getModule().getContext().getTable().getBoolean(value);
   }
   
   public static PrimitiveValue getString(Scope scope, String value) {
      return scope.getModule().getContext().getTable().getString(value);
   }
}