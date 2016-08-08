package org.snapscript.core.bind;

import java.util.concurrent.Callable;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;

public class FunctionCall implements Callable<Result> {
   
   private final FunctionPointer pointer;
   private final Object source;
   private final Scope scope;
   
   public FunctionCall(FunctionPointer pointer, Scope scope, Object source) {
      this.pointer = pointer;
      this.source = source;
      this.scope = scope;
   }
   
   @Override
   public Result call() throws Exception {
      return pointer.call(scope, source);
   }
}
