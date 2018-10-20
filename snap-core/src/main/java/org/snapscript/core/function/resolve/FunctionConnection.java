package org.snapscript.core.function.resolve;

import org.snapscript.core.function.Connection;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public class FunctionConnection implements Connection<Value> {
   
   protected final FunctionCall call;
   
   public FunctionConnection(FunctionCall call) {
      this.call = call;
   }

   @Override
   public boolean match(Scope scope, Object object, Object... arguments) throws Exception {
      return call.match(scope, object, arguments);
   }
   
   @Override
   public Object invoke(Scope scope, Value value, Object... arguments) throws Exception {
      Object source = value.getValue();
      return call.invoke(scope, source, arguments);
   }
}