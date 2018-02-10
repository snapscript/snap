package org.snapscript.core.function;

import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class FunctionHandle {
   
   private final InvocationBuilder builder;
   private final Statement statement;
   private final Function function;
   
   public FunctionHandle(InvocationBuilder builder, Function function, Statement statement) {
      this.statement = statement;
      this.function = function;
      this.builder = builder;
   }
   
   public void validate(Scope scope) throws Exception {
      statement.validate(scope);
   }
   
   public void compile(Scope scope) throws Exception {
      builder.create(scope);
   }
   
   public Function create(Scope scope) throws Exception {
      return function;
   }
}