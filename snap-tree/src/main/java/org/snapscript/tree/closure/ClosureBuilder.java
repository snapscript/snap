package org.snapscript.tree.closure;

import static org.snapscript.core.Reserved.METHOD_CLOSURE;

import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;

public class ClosureBuilder {

   private final Statement statement;
   
   public ClosureBuilder(Statement statement) {
      this.statement = statement;
   }

   public Function create(Signature signature, Scope scope) {
      return create(signature, scope, 0);
   }
   
   public Function create(Signature signature, Scope scope, int modifiers) {
      Invocation invocation = new ClosureInvocation(signature, statement, scope);
      return new InvocationFunction(signature, invocation, null, null, METHOD_CLOSURE, modifiers);
   }
}