package org.snapscript.core.function.dispatch;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.find.FunctionCall;
import org.snapscript.core.function.find.FunctionFinder;

public class ObjectDispatcher implements FunctionDispatcher<Object> {
   
   private final FunctionFinder binder;
   private final ErrorHandler handler;
   private final String name;    
   
   public ObjectDispatcher(FunctionFinder binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Type object, Type... arguments) throws Exception {
      FunctionCall call = binder.bindInstance(scope, object, name, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, object, name, arguments);
      }
      return call.check();
   }
   
   @Override
   public Value dispatch(Scope scope, Object object, Object... arguments) throws Exception {
      FunctionCall call = binder.bindInstance(scope, object, name, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, object, name, arguments);
      }
      return call.call();
   }
}