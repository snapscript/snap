package org.snapscript.core.function;

import org.snapscript.core.Scope;

public class FunctionHandle {
   
   private final InvocationBuilder builder;
   private final Function function;
   
   public FunctionHandle(InvocationBuilder builder, Function function) {
      this.function = function;
      this.builder = builder;
   }
   
   public Function compile(Scope scope) throws Exception {
      return function;
   }
   
   public Invocation create(Scope scope) throws Exception {
      return builder.create(scope);
   }
}
