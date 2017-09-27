package org.snapscript.tree.function;

import org.snapscript.core.Identity;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.function.Invocation;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.dispatch.InvocationBinder;
import org.snapscript.tree.dispatch.InvocationDispatcher;

public class FunctionReferenceInvocation implements Invocation {

   private final FunctionReferenceAligner aligner;
   private final InvocationBinder binder;
   private final NameReference reference;
   private final Identity identity;
   private final Module module;
   private final Object value;
   
   public FunctionReferenceInvocation(Module module, Object value, String method) {
      this.aligner = new FunctionReferenceAligner(value, method);
      this.identity = new Identity(method);
      this.reference = new NameReference(identity);
      this.binder = new InvocationBinder(reference);
      this.module = module;
      this.value = value;
   }

   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      Scope actual = module.getScope();
      Object[] arguments = aligner.align(list); // align constructor arguments
      InvocationDispatcher dispatcher = binder.bind(actual, value);
      Value result = dispatcher.dispatch(actual, value, arguments);
      
      return result.getValue();
   }
}