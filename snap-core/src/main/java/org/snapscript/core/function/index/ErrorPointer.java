package org.snapscript.core.function.index;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.EmptyFunction;
import org.snapscript.core.function.ErrorSignature;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class ErrorPointer implements FunctionPointer {

   private final Signature signature;
   private final Function function;
   
   public ErrorPointer() {
      this.signature = new ErrorSignature();
      this.function = new EmptyFunction(signature);
   }

   @Override
   public ReturnType getType(Scope scope) {
      return new ReturnType(null, scope);
   }

   @Override
   public Function getFunction() {
      return function;
   }

   @Override
   public Invocation getInvocation() {
      return null;
   }
   
   @Override
   public String toString() {
      return String.valueOf(function);
   }
}
