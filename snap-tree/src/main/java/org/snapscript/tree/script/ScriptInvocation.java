package org.snapscript.tree.script;

import static org.snapscript.core.scope.extract.ScopePolicy.EXECUTE_SCRIPT;

import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.extract.ScopePolicyExtractor;

public class ScriptInvocation implements Invocation<Object> {

   private final ScopePolicyExtractor extractor;
   private final InvocationBuilder builder;
   
   public ScriptInvocation(InvocationBuilder builder, Signature signature) {
      this.extractor = new ScopePolicyExtractor(EXECUTE_SCRIPT);
      this.builder = builder;
   }
   
   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      Scope capture = extractor.extract(scope);
      Invocation invocation = builder.create(capture);

      return invocation.invoke(capture, object, list);
   }
}