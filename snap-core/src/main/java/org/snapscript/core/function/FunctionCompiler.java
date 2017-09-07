package org.snapscript.core.function;

import org.snapscript.core.Scope;

public class FunctionCompiler {
   
   private final InvocationBuilder builder;
   private final Function function;
   
   public FunctionCompiler(InvocationBuilder builder, Function function) {
      this.function = function;
      this.builder = builder;
   }
   
   public Function create(Scope scope) throws Exception {
      return function;
   }
   
   public Invocation compile(Scope scope) throws Exception {
      return builder.create(scope);
   }
}
