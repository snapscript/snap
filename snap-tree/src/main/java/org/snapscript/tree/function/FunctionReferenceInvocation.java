package org.snapscript.tree.function;

import org.snapscript.core.Identity;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.dispatch.FunctionDispatcher;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.tree.NameReference;

public class FunctionReferenceInvocation implements Invocation {

   private final FunctionReferenceAligner aligner;
   private final NameReference reference;
   private final FunctionHolder holder;
   private final Identity identity;
   private final Module module;
   private final Object value;
   
   public FunctionReferenceInvocation(Module module, Object value, String method) {
      this.aligner = new FunctionReferenceAligner(value, method);
      this.identity = new Identity(method);
      this.reference = new NameReference(identity);
      this.holder = new FunctionHolder(reference);
      this.module = module;
      this.value = value;
   }

   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      Scope actual = module.getScope();
      Object[] arguments = aligner.align(list); // align constructor arguments
      FunctionDispatcher dispatcher = holder.get(actual, value);
      Value result = dispatcher.dispatch(actual, value, arguments);
      
      return result.getValue();
   }
}