package org.snapscript.core.dispatch;

import org.snapscript.core.AnyType;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.bind.InvocationTask;
import org.snapscript.core.error.ErrorHandler;

public class LocalDispatcher implements CallDispatcher<Object> {
   
   private final FunctionBinder binder;
   private final ErrorHandler handler;
   private final String name;  
   
   public LocalDispatcher(FunctionBinder binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }

   @Override
   public Type validate(Scope scope, Type object, Type... arguments) throws Exception {
//      if(object != null) {
//         InvocationTask call = bind(scope, object, arguments);
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
      InvocationTask call = bind(scope, object, arguments);
      return call.call();
   }
   
   private InvocationTask bind(Scope scope, Object object, Object... arguments) throws Exception {
      Module module = scope.getModule();
      InvocationTask local = binder.bindModule(scope, module, name, arguments);
      
      if(local == null) {
         InvocationTask closure = binder.bindScope(scope, name, arguments); // function variable
         
         if(closure != null) {
            return closure;   
         }
      }
      if(local == null) {
         handler.throwInternalException(scope, name, arguments);
      }
      return local;  
   }
   
}