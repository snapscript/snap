package org.snapscript.tree.math;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.ValueCache;

public class ShortCalculator extends IntegerCalculator {

   @Override
   public Value and(Scope scope, Number left, Number right) {
      short first = left.shortValue();
      short second = right.shortValue();
      
      return ValueCache.getShort(scope, first & second);
   }

   @Override
   public Value or(Scope scope, Number left, Number right) {
      short first = left.shortValue();
      short second = right.shortValue();
      
      return ValueCache.getShort(scope, first | second);
   }

   @Override
   public Value xor(Scope scope, Number left, Number right) {
      short first = left.shortValue();
      short second = right.shortValue();
      
      return ValueCache.getShort(scope, first ^ second);
   }
}