package org.snapscript.core.bind;

import java.util.concurrent.Callable;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class FunctionCall implements Callable<Value> {
   
   private final FunctionPointer pointer;
   private final Object source;
   private final Scope scope;
   
   public FunctionCall(FunctionPointer pointer, Scope scope, Object source) {
      this.pointer = pointer;
      this.source = source;
      this.scope = scope;
   }
   
   @Override
   public Value call() throws Exception {
      Object result = pointer.call(scope, source);
      return Value.getTransient(result);
   }
}