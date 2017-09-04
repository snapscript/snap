package org.snapscript.tree.script;

import org.snapscript.core.LocalScopeExtractor;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;

public class ScriptInvocation implements Invocation<Object> {

   private final LocalScopeExtractor extractor;
   private final InvocationBuilder builder;
   private final SignatureAligner aligner;
   
   public ScriptInvocation(InvocationBuilder builder, Signature signature) {
      this.aligner = new SignatureAligner(signature);
      this.extractor = new LocalScopeExtractor();
      this.builder = builder;
   }
   
   @Override
   public Result invoke(Scope scope, Object object, Object... list) throws Exception {
      Object[] arguments = aligner.align(list); 
      Scope capture = extractor.extract(scope);
      Invocation invocation = builder.create(capture);

      return invocation.invoke(capture, object, arguments);
   }
}