package org.snapscript.tree.math;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.ValueCache;

public class IntegerCalculator extends ValueCalculator {

   @Override
   public Value add(Scope scope, Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(scope, first + second);
   }

   @Override
   public Value subtract(Scope scope, Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(scope, first - second);
   }

   @Override
   public Value divide(Scope scope, Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(scope, first / second);
   }

   @Override
   public Value multiply(Scope scope, Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(scope, first * second);
   }

   @Override
   public Value modulus(Scope scope, Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(scope, first % second);
   }

   @Override
   public Value shiftLeft(Scope scope, Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(scope, first << second);
   }

   @Override
   public Value shiftRight(Scope scope, Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(scope, first >> second);
   }

   @Override
   public Value unsignedShiftRight(Scope scope, Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(scope, first >>> second);
   }

   @Override
   public Value and(Scope scope, Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(scope, first & second);
   }

   @Override
   public Value or(Scope scope, Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(scope, first | second);
   }

   @Override
   public Value xor(Scope scope, Number left, Number right) {
      int first = left.intValue();
      int second = right.intValue();
      
      return ValueCache.getInteger(scope, first ^ second);
   }
   
   @Override
   public Value power(Scope scope, Number left, Number right) {
      double first = left.doubleValue();
      double second = right.doubleValue();
      double result = Math.pow(first, second);
      
      return ValueCache.getDouble(scope, result);
   }
}