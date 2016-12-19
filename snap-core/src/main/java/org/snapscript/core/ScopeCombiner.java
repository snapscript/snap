package org.snapscript.core;

public class ScopeCombiner {

   public static Scope combine(Scope inner, Scope outer) {
      return new CompoundScope(null, inner, outer);
   }
}
