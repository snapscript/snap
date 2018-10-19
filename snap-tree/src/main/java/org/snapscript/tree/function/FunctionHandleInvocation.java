package org.snapscript.tree.function;

import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.bind.FunctionMatcher;
import org.snapscript.core.function.dispatch.FunctionDispatcher;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public class FunctionHandleInvocation implements Invocation {

   private final FunctionHandleAligner aligner;
   private final FunctionMatcher matcher;
   private final Module module;
   private final Value value;

   public FunctionHandleInvocation(FunctionMatcher matcher, Module module, Value value) {
      this(matcher, module, value, false);
   }
   
   public FunctionHandleInvocation(FunctionMatcher matcher, Module module, Value value, boolean constructor) {
      this.aligner = new FunctionHandleAligner(value, constructor);
      this.matcher = matcher;
      this.module = module;
      this.value = value;
   }

   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      Scope actual = module.getScope();
      Object[] arguments = aligner.align(list); // align constructor arguments
      FunctionDispatcher dispatcher = matcher.match(actual, value);
      
      return dispatcher.dispatch(actual, value, arguments).invoke(actual, value, arguments);
   }
}