package org.snapscript.core.function.dispatch;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.Delegate;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.find.FunctionCall;
import org.snapscript.core.function.find.FunctionFinder;

public class DelegateDispatcher implements FunctionDispatcher<Delegate> {
   
   private final FunctionFinder binder;
   private final ErrorHandler handler;
   private final String name;      
   
   public DelegateDispatcher(FunctionFinder binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Type object, Type... arguments) throws Exception {
      FunctionCall call = binder.bindFunction(scope, object, name, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, object, name, arguments);
      }
      return call.check();
   }
   
   @Override
   public Value dispatch(Scope scope, Delegate object, Object... arguments) throws Exception {
      FunctionCall call = binder.bindFunction(scope, object, name, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, object, name, arguments);
      }
      return call.call();
   }
}