package org.snapscript.core.dispatch;

import org.snapscript.core.AnyType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.bind.InvocationTask;
import org.snapscript.core.error.ErrorHandler;

public class ObjectDispatcher implements CallDispatcher<Object> {
   
   private final FunctionBinder binder;
   private final ErrorHandler handler;
   private final String name;    
   
   public ObjectDispatcher(FunctionBinder binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Type validate(Scope scope, Type object, Type... arguments) throws Exception {
//      if(object != null && object.getClass() != Object.class) {
//         InvocationTask call = binder.bind(scope, object, name, arguments);
//         if(call == null) {
//            handler.throwInternalException(scope, object, name, arguments);
//         }
//         Object o = null;
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
   public Value dispatch(Scope scope, Object object, Object... arguments) throws Exception {
      InvocationTask call = binder.bindInstance(scope, object, name, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, object, name, arguments);
      }
      return call.call();
   }
}