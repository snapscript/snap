package org.snapscript.core.function.index;

import static org.snapscript.core.function.index.Retention.NEVER;

import org.snapscript.core.function.EmptyFunction;
import org.snapscript.core.function.ErrorSignature;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;
import org.snapscript.core.scope.Scope;

public class ErrorPointer implements FunctionPointer {

   private final Signature signature;
   private final Function function;
   
   public ErrorPointer() {
      this.signature = new ErrorSignature();
      this.function = new EmptyFunction(signature);
   }

   @Override
   public ReturnType getType(Scope scope) {
      return new AttributeType(null, scope);
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
   public Retention getRetention() {
      return NEVER;
   }
   
   @Override
   public String toString() {
      return String.valueOf(function);
   }
}
