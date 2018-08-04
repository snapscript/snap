package org.snapscript.tree.math;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.ValueCache;

public class ByteCalculator extends IntegerCalculator {

   @Override
   public Value and(Scope scope, Number left, Number right) {
      byte first = left.byteValue();
      byte second = right.byteValue();
      
      return ValueCache.getByte(scope, first & second);
   }

   @Override
   public Value or(Scope scope, Number left, Number right) {
      byte first = left.byteValue();
      byte second = right.byteValue();
      
      return ValueCache.getByte(scope, first | second);
   }

   @Override
   public Value xor(Scope scope, Number left, Number right) {
      byte first = left.byteValue();
      byte second = right.byteValue();
      
      return ValueCache.getByte(scope, first ^ second);
   }
}