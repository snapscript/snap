package org.snapscript.core.function.dispatch;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.search.FunctionCall;
import org.snapscript.core.function.search.FunctionSearcher;

public class TypeDispatcher implements FunctionDispatcher<Type> {
   
   private final FunctionSearcher binder;
   private final ErrorHandler handler;
   private final String name;
   
   public TypeDispatcher(FunctionSearcher binder, ErrorHandler handler, String name) {
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
      FunctionCall call = binder.searchStatic(scope, type, name, arguments);
      
      if(call == null) {
         return binder.searchInstance(scope, (Object)type, name, arguments);
      }
      return call;
   }
}