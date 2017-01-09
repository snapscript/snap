package org.snapscript.tree.function;

import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.function.Invocation;
import org.snapscript.tree.dispatch.InvocationBinder;
import org.snapscript.tree.dispatch.InvocationDispatcher;

public class FunctionReferenceInvocation implements Invocation {

   private final FunctionReferenceAligner aligner;
   private final InvocationBinder binder;
   private final Module module;
   private final String method;
   private final Object value;
   
   public FunctionReferenceInvocation(Module module, Object value, String method) {
      this.aligner = new FunctionReferenceAligner(value, method);
      this.binder = new InvocationBinder();
      this.module = module;
      this.method = method;
      this.value = value;
   }

   @Override
   public Result invoke(Scope scope, Object object, Object... list) throws Exception {
      Scope actual = module.getScope();
      Object[] arguments = aligner.align(list); // align constructor arguments
      InvocationDispatcher dispatcher = binder.bind(actual, value);
      Value value = dispatcher.dispatch(method, arguments);
      Object result = value.getValue();
    
      return ResultType.getNormal(result);
   }
}
