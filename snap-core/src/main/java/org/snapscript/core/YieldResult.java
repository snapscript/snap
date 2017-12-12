package org.snapscript.core;

import static org.snapscript.core.ResultType.YIELD;

public class YieldResult extends Result {

   private final Resume next;
   private final Scope scope;
   
   public YieldResult() {
      this(null);
   }
   
   public YieldResult(Object value) {
      this(value, null, null);
   }
   
   public YieldResult(Object value, Scope scope, Resume next) {
      super(YIELD, value);
      this.scope = scope;
      this.next = next;
   }
   
   @Override
   public Yield getValue() {
      return new Yield(value, scope, next);
   }
}
