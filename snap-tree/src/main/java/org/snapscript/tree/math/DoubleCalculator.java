package org.snapscript.tree.math;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.ValueCache;

public class DoubleCalculator extends LongCalculator {

   @Override
   public Value add(Scope scope, Number left, Number right) {
      double first = left.doubleValue();
      double second = right.doubleValue();
      
      return ValueCache.getDouble(scope, first + second);
   }

   @Override
   public Value subtract(Scope scope, Number left, Number right) {
      double first = left.doubleValue();
      double second = right.doubleValue();
      
      return ValueCache.getDouble(scope, first - second);
   }

   @Override
   public Value divide(Scope scope, Number left, Number right) {
      double first = left.doubleValue();
      double second = right.doubleValue();
      
      return ValueCache.getDouble(scope, first / second);
   }

   @Override
   public Value multiply(Scope scope, Number left, Number right) {
      double first = left.doubleValue();
      double second = right.doubleValue();
      
      return ValueCache.getDouble(scope, first * second);
   }

   @Override
   public Value modulus(Scope scope, Number left, Number right) {
      double first = left.doubleValue();
      double second = right.doubleValue();
      
      return ValueCache.getDouble(scope, first % second);
   }
}