package org.snapscript.tree.function;

import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;

public class FunctionBuilder {

   private final Statement statement;
   
   public FunctionBuilder(Statement statement) {
      this.statement = statement;
   }

   public Function create(Signature signature, Type constraint, String name) {
      Invocation invocation = new StatementInvocation(signature, statement, constraint);
      return new InvocationFunction(signature, invocation, null, constraint, name, 0);
   }
}