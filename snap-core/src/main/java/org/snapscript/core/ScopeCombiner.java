package org.snapscript.core;

@Bug("remove me")
public class ScopeCombiner {

   public static Scope combine(Scope inner, Scope outer) {
      return new CompoundScope(inner, outer);
   }
}