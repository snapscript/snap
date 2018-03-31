package org.snapscript.core.function.dispatch;

import java.util.List;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.array.ArrayBuilder;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.find.FunctionCall;
import org.snapscript.core.function.find.FunctionFinder;

public class ArrayDispatcher implements FunctionDispatcher<Object> {
   
   private final FunctionFinder binder;
   private final ArrayBuilder builder;
   private final ErrorHandler handler;
   private final String name;
   
   public ArrayDispatcher(FunctionFinder binder, ErrorHandler handler, String name) {
      this.builder = new ArrayBuilder();
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Type object, Type... arguments) throws Exception {
      Type list = builder.convert(object);
      FunctionCall call = binder.bindInstance(scope, list, name, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, object, name, arguments);
      }
      return call.check();
   }

   @Override
   public Value dispatch(Scope scope, Object object, Object... arguments) throws Exception {
      List list = builder.convert(object);
      FunctionCall call = binder.bindInstance(scope, list, name, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, object, name, arguments);
      }
      return call.call();
   }
}