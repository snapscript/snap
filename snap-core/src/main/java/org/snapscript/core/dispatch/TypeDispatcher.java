package org.snapscript.core.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.error.ErrorHandler;

public class TypeDispatcher implements CallDispatcher<Type> {
   
   private final FunctionBinder binder;
   private final ErrorHandler handler;
   private final String name;
   
   public TypeDispatcher(FunctionBinder binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }

   @Override
   public Value dispatch(Scope scope, Type type, Object... arguments) throws Exception {   
      Callable<Value> call = bind(scope, type, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, type, name, arguments);
      }
      return call.call();          
   } 
   
   private Callable<Value> bind(Scope scope, Type type, Object... arguments) throws Exception {
      Callable<Value> call = binder.bind(scope, type, name, arguments);
      
      if(call == null) {
         return binder.bind(scope, (Object)type, name, arguments);
      }
      return call;
   }
}