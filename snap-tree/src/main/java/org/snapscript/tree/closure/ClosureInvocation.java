package org.snapscript.tree.closure;

import org.snapscript.core.Scope;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;

public class ClosureInvocation implements Invocation<Object> {

   private final InvocationBuilder builder;
   private final SignatureAligner aligner;
   private final Scope outer;
   
   public ClosureInvocation(InvocationBuilder builder, Signature signature, Scope outer) {
      this.aligner = new SignatureAligner(signature);
      this.builder = builder;
      this.outer = outer;
   }
   
   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      Object[] arguments = aligner.align(list); 
      Invocation invocation = builder.create(outer);

      return invocation.invoke(outer, object, arguments);
   }
}