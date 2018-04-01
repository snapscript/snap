package org.snapscript.core.yield;

import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;

public class YieldResult extends Result {

   private final Object value;
   private final Resume next;
   private final Scope scope;
   
   public YieldResult() {
      this(null);
   }
   
   public YieldResult(Object value) {
      this(value, null, null);
   }
   
   public YieldResult(Object value, Scope scope, Resume next) {
      this.value = value;
      this.scope = scope;
      this.next = next;
   }
   
   @Override
   public boolean isYield() {
      return true;
   }
   
   @Override
   public Yield getValue() {
      return new Yield(value, scope, next);
   }
}
