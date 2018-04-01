package org.snapscript.tree.script;

import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.LocalScopeExtractor;

public class ScriptInvocation implements Invocation<Object> {

   private final LocalScopeExtractor extractor;
   private final InvocationBuilder builder;
   
   public ScriptInvocation(InvocationBuilder builder, Signature signature) {
      this.extractor = new LocalScopeExtractor(true, false);
      this.builder = builder;
   }
   
   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      Scope capture = extractor.extract(scope);
      Invocation invocation = builder.create(capture);

      return invocation.invoke(capture, object, list);
   }
}