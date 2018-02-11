package org.snapscript.core.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.AnyType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.convert.Delegate;
import org.snapscript.core.error.ErrorHandler;

public class DelegateDispatcher implements CallDispatcher<Delegate> {
   
   private final FunctionBinder binder;
   private final ErrorHandler handler;
   private final String name;      
   
   public DelegateDispatcher(FunctionBinder binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Type validate(Scope scope, Type object, Type... arguments) throws Exception {
//      if(object != null && object.getClass() != Object.class) {
//         InvocationTask call = binder.bind(scope, object, name, arguments);
//         Object o = null;
//         
//         if(call == null) {
//            handler.throwInternalException(scope, object, name, arguments);
//         }
//         Type type = call.getReturn();
//         if(type != null) {
//            o = scope.getModule().getContext().getProvider().create().createShellConstructor(type).invoke(scope, null, null);
//         } else {
//            o = new Object();
//         }
//         return Value.getTransient(o);
//      }
//      return Value.getTransient(new Object());
      return new AnyType(scope);
   }
   
   @Override
   public Value dispatch(Scope scope, Delegate object, Object... arguments) throws Exception {
      Callable<Value> call = binder.bindFunction(scope, object, name, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, object, name, arguments);
      }
      return call.call();
   }
}