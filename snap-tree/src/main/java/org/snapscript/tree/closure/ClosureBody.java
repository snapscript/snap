package org.snapscript.tree.closure;

import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionBody;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.scope.Scope;

public class ClosureBody extends FunctionBody {

   public ClosureBody(InvocationBuilder builder, InvocationBuilder other, Function function) {
      super(builder, other, function);
   }
   
   public Function create(Scope scope) throws Exception {
      Invocation invocation = new ClosureInvocation(actual, scope);
      Function closure = new ClosureFunction(function, invocation);
      
      return closure;
   }
}
