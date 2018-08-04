package org.snapscript.tree.math;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.ValueCache;

public class LongCalculator extends IntegerCalculator {

   @Override
   public Value add(Scope scope, Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getLong(scope, first + second);
   }

   @Override
   public Value subtract(Scope scope, Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getLong(scope, first - second);
   }

   @Override
   public Value divide(Scope scope, Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getLong(scope, first / second);
   }

   @Override
   public Value multiply(Scope scope, Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getLong(scope, first * second);
   }

   @Override
   public Value modulus(Scope scope, Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getLong(scope, first % second);
   }

   @Override
   public Value shiftLeft(Scope scope, Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getLong(scope, first << second);
   }

   @Override
   public Value shiftRight(Scope scope, Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getLong(scope, first >> second);
   }

   @Override
   public Value unsignedShiftRight(Scope scope, Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getLong(scope, first >>> second);
   }

   @Override
   public Value and(Scope scope, Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getLong(scope, first & second);
   }

   @Override
   public Value or(Scope scope, Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getLong(scope, first | second);
   }

   @Override
   public Value xor(Scope scope, Number left, Number right) {
      long first = left.longValue();
      long second = right.longValue();
      
      return ValueCache.getLong(scope, first ^ second);
   }
}