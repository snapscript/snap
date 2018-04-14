package org.snapscript.core.function.index;

import org.snapscript.core.function.EmptyFunction;
import org.snapscript.core.function.EmptySignature;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;

public class EmptyPointer implements FunctionPointer {

   private final Signature signature;
   private final Function function;
   
   public EmptyPointer() {
      this.signature = new EmptySignature();
      this.function = new EmptyFunction(signature);
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
