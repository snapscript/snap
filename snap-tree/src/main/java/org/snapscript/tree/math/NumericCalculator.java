package org.snapscript.tree.math;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public interface NumericCalculator {
   Value power(Scope scope, Number left, Number right);
   Value add(Scope scope, Number left, Number right);
   Value subtract(Scope scope, Number left, Number right);
   Value divide(Scope scope, Number left, Number right);
   Value multiply(Scope scope, Number left, Number right);
   Value modulus(Scope scope, Number left, Number right);
   Value shiftLeft(Scope scope, Number left, Number right);
   Value shiftRight(Scope scope, Number left, Number right);
   Value unsignedShiftRight(Scope scope, Number left, Number right);
   Value and(Scope scope, Number left, Number right);
   Value or(Scope scope, Number left, Number right);
   Value xor(Scope scope, Number left, Number right);
}