package org.snapscript.core;

import org.snapscript.core.State;

public class ScopeCombiner {

   public static Scope combine(State stack, Scope inner, Scope outer) {
      return new CompoundScope(stack, null, inner, outer);
   }
}
