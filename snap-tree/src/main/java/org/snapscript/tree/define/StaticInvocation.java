package org.snapscript.tree.define;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;

public class StaticInvocation implements Invocation<Object> {

   private final InvocationBuilder builder;
   private final SignatureAligner aligner;
   private final Scope inner;
   
   public StaticInvocation(InvocationBuilder builder, Signature signature, Scope inner) {
      this.aligner = new SignatureAligner(signature);
      this.builder = builder;
      this.inner = inner;
   }
   
   @Override
   public Result invoke(Scope outer, Object object, Object... list) throws Exception {
      Object[] arguments = aligner.align(list); 
      Invocation invocation = builder.create(inner);
      
      return invocation.invoke(inner, object, arguments);
   }
}