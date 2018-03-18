package org.snapscript.tree.function;

import org.snapscript.core.Scope;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;

public class StatementInvocation implements Invocation<Object> {

   private final InvocationBuilder builder;

   public StatementInvocation(InvocationBuilder builder) {
      this.builder = builder;
   }
   
   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      Scope outer = scope.getScope(); 
      Invocation invocation = builder.define(outer); // what if the body is compiled
      
      return invocation.invoke(outer, object, list);
   }
}