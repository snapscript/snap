package org.snapscript.core.function.index;

import static org.snapscript.core.function.index.Retention.ALWAYS;
import static org.snapscript.core.function.index.Retention.NEVER;

import java.util.Set;

import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.scope.Scope;

public class CachePointer implements FunctionPointer {

   private final FunctionPointer pointer;
   private final Set keys;

   public CachePointer(FunctionPointer pointer, Set keys) {
      this.pointer = pointer;
      this.keys = keys;
   }

   @Override
   public ReturnType getType(Scope scope) {
      return pointer.getType(scope);
   }

   @Override
   public Function getFunction() {
      return pointer.getFunction();
   }

   @Override
   public Invocation getInvocation() {
      return pointer.getInvocation();
   }

   @Override
   public Retention getRetention() {
      return 1 == keys.size() ? ALWAYS : NEVER;
   }
   
   @Override
   public String toString() {
      return pointer.toString();
   }
}