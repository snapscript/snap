package org.snapscript.tree.math;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public abstract class ValueCalculator implements NumericCalculator {
   
   public static final ValueCalculator INTEGER = new IntegerCalculator();
   public static final ValueCalculator LONG = new LongCalculator();
   public static final ValueCalculator FLOAT = new FloatCalculator();
   public static final ValueCalculator DOUBLE = new DoubleCalculator();
   public static final ValueCalculator SHORT = new ShortCalculator();
   public static final ValueCalculator BYTE = new ByteCalculator();
   
   public Value replace(Scope scope, Value left, Value right) {
      return right;
   }

   public Value coalesce(Scope scope, Value left, Value right) {
      Object primary = left.getValue();
      Object secondary = right.getValue();
      
      return primary == null ? right : left;
   }
   
   public Value power(Scope scope, Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return power(scope, primary, secondary);
   }
   
   public Value add(Scope scope, Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return add(scope, primary, secondary);
   }
   
   public Value subtract(Scope scope, Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return subtract(scope, primary, secondary);
   }
   
   public Value divide(Scope scope, Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return divide(scope, primary, secondary);
   }
   
   public Value multiply(Scope scope, Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return multiply(scope, primary, secondary);
   }
   
   public Value modulus(Scope scope, Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return modulus(scope, primary, secondary);
   }
   
   public Value shiftLeft(Scope scope, Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return shiftLeft(scope, primary, secondary);
   }
   
   public Value shiftRight(Scope scope, Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return shiftRight(scope, primary, secondary);
   }
   
   public Value unsignedShiftRight(Scope scope, Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return unsignedShiftRight(scope, primary, secondary);
   }
   
   public Value and(Scope scope, Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return and(scope, primary, secondary);
   }
   
   public Value or(Scope scope, Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return or(scope, primary, secondary);
   }
   
   public Value xor(Scope scope, Value left, Value right) {
      Number primary = left.getNumber();
      Number secondary = right.getNumber();
      
      return xor(scope, primary, secondary);
   }
}
