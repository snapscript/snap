package org.snapscript.tree.function;

import org.snapscript.core.Scope;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;

public class StatementInvocation implements Invocation<Object> {

   private final InvocationBuilder builder;
   private final SignatureAligner aligner;

   public StatementInvocation(InvocationBuilder builder, Signature signature) {
      this.aligner = new SignatureAligner(signature);
      this.builder = builder;
   }
   
   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      Object[] arguments = aligner.align(list); 
      Scope outer = scope.getOuter(); 
      Invocation invocation = builder.create(outer); // what if the body is compiled
      
      return invocation.invoke(outer, object, arguments);
   }
}