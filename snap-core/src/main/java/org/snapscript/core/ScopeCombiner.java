package org.snapscript.core;

public class ScopeCombiner {

   public static Scope combine(Stack stack, Scope inner, Scope outer) {
      return new CompoundScope(stack, null, inner, outer);
   }
}
