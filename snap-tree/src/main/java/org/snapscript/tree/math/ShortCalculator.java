package org.snapscript.tree.math;

import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.ValueCache;

public class ShortCalculator extends IntegerCalculator {

   @Override
   public Value and(Number left, Number right) {
      short first = left.shortValue();
      short second = right.shortValue();
      
      return ValueCache.getShort(first & second);
   }

   @Override
   public Value or(Number left, Number right) {
      short first = left.shortValue();
      short second = right.shortValue();
      
      return ValueCache.getShort(first | second);
   }

   @Override
   public Value xor(Number left, Number right) {
      short first = left.shortValue();
      short second = right.shortValue();
      
      return ValueCache.getShort(first ^ second);
   }
}