package org.snapscript.tree.function;

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.function.Invocation;
import org.snapscript.tree.dispatch.InvocationBinder;
import org.snapscript.tree.dispatch.InvocationDispatcher;

public class FunctionReferenceInvocation implements Invocation {

   private final InvocationBinder binder;
   private final String method;
   private final Object value;
   
   public FunctionReferenceInvocation(Object value, String method) {
      this.binder = new InvocationBinder();
      this.method = method;
      this.value = value;
   }

   @Override
   public Result invoke(Scope scope, Object object, Object... list) throws Exception {
      InvocationDispatcher dispatcher = binder.bind(scope, value);
      Value value = dispatcher.dispatch(method, list);
      Object result = value.getValue();
      
      return ResultType.getNormal(result);
   }
}
