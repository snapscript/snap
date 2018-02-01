package org.snapscript.core.dispatch;

import java.util.List;
import java.util.concurrent.Callable;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.array.ArrayBuilder;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.error.ErrorHandler;

public class ArrayDispatcher implements CallDispatcher<Object> {
   
   private final FunctionBinder binder;
   private final ArrayBuilder builder;
   private final ErrorHandler handler;
   private final String name;
   
   public ArrayDispatcher(FunctionBinder binder, ErrorHandler handler, String name) {
      this.builder = new ArrayBuilder();
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }

   @Override
   public Value dispatch(Scope scope, Object object, Object... arguments) throws Exception {
      List list = builder.convert(object);
      Callable<Value> call = binder.bind(scope, list, name, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, object, name, arguments);
      }
      return call.call();
   }
}