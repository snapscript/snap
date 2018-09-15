package org.snapscript.core.function.index;

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
   public boolean isCachable() {
      return 1 == keys.size();
   }
}