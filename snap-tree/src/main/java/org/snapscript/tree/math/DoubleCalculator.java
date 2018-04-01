package org.snapscript.tree.math;

import org.snapscript.core.scope.Value;
import org.snapscript.core.scope.ValueCache;

public class DoubleCalculator extends LongCalculator {

   @Override
   public Value add(Number left, Number right) {
      double first = left.doubleValue();
      double second = right.doubleValue();
      
      return ValueCache.getDouble(first + second);
   }

   @Override
   public Value subtract(Number left, Number right) {
      double first = left.doubleValue();
      double second = right.doubleValue();
      
      return ValueCache.getDouble(first - second);
   }

   @Override
   public Value divide(Number left, Number right) {
      double first = left.doubleValue();
      double second = right.doubleValue();
      
      return ValueCache.getDouble(first / second);
   }

   @Override
   public Value multiply(Number left, Number right) {
      double first = left.doubleValue();
      double second = right.doubleValue();
      
      return ValueCache.getDouble(first * second);
   }

   @Override
   public Value modulus(Number left, Number right) {
      double first = left.doubleValue();
      double second = right.doubleValue();
      
      return ValueCache.getDouble(first % second);
   }
}