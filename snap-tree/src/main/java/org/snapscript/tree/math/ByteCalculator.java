package org.snapscript.tree.math;

import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.ValueCache;

public class ByteCalculator extends IntegerCalculator {

   @Override
   public Value and(Number left, Number right) {
      byte first = left.byteValue();
      byte second = right.byteValue();
      
      return ValueCache.getByte(first & second);
   }

   @Override
   public Value or(Number left, Number right) {
      byte first = left.byteValue();
      byte second = right.byteValue();
      
      return ValueCache.getByte(first | second);
   }

   @Override
   public Value xor(Number left, Number right) {
      byte first = left.byteValue();
      byte second = right.byteValue();
      
      return ValueCache.getByte(first ^ second);
   }
}