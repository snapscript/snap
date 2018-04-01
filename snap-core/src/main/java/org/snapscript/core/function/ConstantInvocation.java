package org.snapscript.core.function;

import org.snapscript.core.function.Invocation;
import org.snapscript.core.scope.Scope;

public class ConstantInvocation implements Invocation {

   private final Object value;
   
   public ConstantInvocation(Object value) {
      this.value = value;
   }
   
   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      return value;
   }
}