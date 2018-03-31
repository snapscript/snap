package org.snapscript.core.function.dispatch;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.find.FunctionCall;
import org.snapscript.core.function.find.FunctionFinder;

public class TypeDispatcher implements FunctionDispatcher<Type> {
   
   private final FunctionFinder binder;
   private final ErrorHandler handler;
   private final String name;
   
   public TypeDispatcher(FunctionFinder binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Type type, Type... arguments) throws Exception {   
      FunctionCall call = binder.bindStatic(scope, type, name, arguments);
      
      if(call == null) {
         call = binder.bindInstance(scope, type, name, arguments);
      }
      if(call == null) {
         handler.throwInternalException(scope, type, name, arguments);
      }
      return call.check();
   } 

   @Override
   public Value dispatch(Scope scope, Type type, Object... arguments) throws Exception {   
      FunctionCall call = bind(scope, type, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, type, name, arguments);
      }
      return call.call();          
   } 
   
   private FunctionCall bind(Scope scope, Type type, Object... arguments) throws Exception {
      FunctionCall call = binder.bindStatic(scope, type, name, arguments);
      
      if(call == null) {
         return binder.bindInstance(scope, (Object)type, name, arguments);
      }
      return call;
   }
}