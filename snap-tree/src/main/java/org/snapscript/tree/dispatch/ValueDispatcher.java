package org.snapscript.tree.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.tree.NameReference;

public class ValueDispatcher implements InvocationDispatcher<Value> {
   
   private final NameReference reference;
   
   public ValueDispatcher(NameReference reference) {
      this.reference = reference;
   }

   @Override
   public Value dispatch(Scope scope, Value value, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      String name = reference.getName(scope);
      Callable<Value> closure = binder.bind(value, arguments); // function variable
      
      if(closure == null) {
         throw new InternalStateException("Method '" + name + "' not found in scope");
      }
      return closure.call();   
   }
   
}