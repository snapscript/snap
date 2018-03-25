package org.snapscript.tree.closure;

import org.snapscript.core.Scope;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionHandle;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;

public class ClosureHandle extends FunctionHandle {

   public ClosureHandle(InvocationBuilder builder, InvocationBuilder other, Function function) {
      super(builder, other, function);
   }
   
   public Function create(Scope scope) throws Exception {
      Invocation invocation = new ClosureInvocation(builder, scope);
      Function closure = new ClosureFunction(function, invocation);
      
      return closure;
   }
}
