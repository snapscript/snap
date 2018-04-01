package org.snapscript.core.function.dispatch;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.search.FunctionCall;
import org.snapscript.core.function.search.FunctionSearcher;

public class TypeStaticDispatcher implements FunctionDispatcher<Type> {
   
   private final FunctionSearcher binder;
   private final ErrorHandler handler;
   private final String name;
   
   public TypeStaticDispatcher(FunctionSearcher binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Type type, Type... arguments) throws Exception {   
      FunctionCall call = binder.searchStatic(scope, type, name, arguments);

      if(call == null) {
         call = binder.searchInstance(scope, type, name, arguments);
      }
      if(call == null) {
         handler.handleCompileError(scope, type, name, arguments);
      }
      return call.check();
   } 

   @Override
   public Value dispatch(Scope scope, Type type, Object... arguments) throws Exception {   
      FunctionCall call = binder.searchStatic(scope, type, name, arguments);

      if(call == null) {
         call = binder.searchInstance(scope, type, name, arguments);
      }
      if(call == null) {
         handler.handleRuntimeError(scope, type, name, arguments);
      }
      return call.call();          
   } 
}