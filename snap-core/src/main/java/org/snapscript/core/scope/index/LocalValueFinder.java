package org.snapscript.core.scope.index;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public class LocalValueFinder {

   private final LocalScopeFinder checker;
   private final AtomicBoolean failure;
   private final String name;

   public LocalValueFinder(String name) {
      this.checker = new LocalScopeFinder();
      this.failure = new AtomicBoolean();
      this.name = name;
   }

   public Value findValue(Scope scope) {
      return findValue(scope, -1);
   }

   public Value findValue(Scope scope, int depth) {
      boolean ignore = failure.get();

      if(!ignore) {
         Value value = checker.findValue(scope, name, depth);

         if(value == null) {
            failure.set(true);
         }
         return value;
      }
      return null;
   }

   public Value findFunction(Scope scope) {
      return findFunction(scope, -1);
   }

   public Value findFunction(Scope scope, int depth) {
      boolean ignore = failure.get();

      if(!ignore) {
         Value value = checker.findFunction(scope, name, depth);

         if(value == null) {
            failure.set(true);
         }
         return value;
      }
      return null;
   }
}
