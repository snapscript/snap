package org.snapscript.core.dispatch;

import org.snapscript.core.AnyType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.bind.InvocationTask;
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
   public Type validate(Scope scope, Type type, Type... arguments) throws Exception {   
      InvocationTask call = binder.bindStatic(scope, type, name, arguments);
      
      if(call == null) {
         call = binder.bindInstance(scope, type, name, arguments);
      }
      if(call == null) {
         handler.throwInternalException(scope, type, name, arguments);
      }
      return call.getReturn();
   } 

   @Override
   public Value dispatch(Scope scope, Type type, Object... arguments) throws Exception {   
      InvocationTask call = bind(scope, type, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, type, name, arguments);
      }
      return call.call();          
   } 
   
   private InvocationTask bind(Scope scope, Type type, Object... arguments) throws Exception {
      InvocationTask call = binder.bindStatic(scope, type, name, arguments);
      
      if(call == null) {
         return binder.bindInstance(scope, (Object)type, name, arguments);
      }
      return call;
   }
}