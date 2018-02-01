package org.snapscript.core.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;

public class ValueDispatcher implements CallDispatcher<Value> {
   
   private final FunctionBinder binder;
   private final String name;
   
   public ValueDispatcher(FunctionBinder binder, String name) {
      this.binder = binder;
      this.name = name;
   }

   @Override
   public Value dispatch(Scope scope, Value value, Object... arguments) throws Exception {
      Callable<Value> closure = binder.bind(value, arguments); // function variable
      
      if(closure == null) {
         throw new InternalStateException("Method '" + name + "' not found in scope");
      }
      return closure.call();   
   }
   
}