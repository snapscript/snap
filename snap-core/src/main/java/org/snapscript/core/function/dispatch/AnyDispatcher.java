package org.snapscript.core.function.dispatch;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.search.FunctionCall;
import org.snapscript.core.function.search.FunctionSearcher;

public class AnyDispatcher implements FunctionDispatcher<Scope> {
   
   private final FunctionSearcher binder;
   private final ErrorHandler handler;
   private final String name;
   
   public AnyDispatcher(FunctionSearcher binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Type object, Type... arguments) throws Exception {
      FunctionCall match = bind(scope, object, arguments);
      
      if(match == null) {
         handler.throwInternalException(scope, name, arguments);
      }
      return match.check();   
   }

   @Override
   public Value dispatch(Scope scope, Scope object, Object... arguments) throws Exception {
      FunctionCall match = bind(scope, object, arguments);
      
      if(match == null) {
         Type type = object.getType();
         
         if(type != null) {
            handler.throwInternalException(scope, type, name, arguments);
         }
         handler.throwInternalException(scope, name, arguments);
      }
      return match.call();          
   }
   
   private FunctionCall bind(Scope scope, Type object, Type... arguments) throws Exception {
      FunctionCall local = binder.searchInstance(scope, object, name, arguments);
      
      if(local == null) {
         Module module = scope.getModule();
         FunctionCall external = binder.searchModule(scope, module, name, arguments); // maybe closure should be first
         
         if(external != null) {
            return external;
         }
         FunctionCall closure = binder.searchScope(scope, name, arguments); // closure
         
         if(closure != null) {
            return closure;
         }
      }
      return local;  
   }
   
   private FunctionCall bind(Scope scope, Scope object, Object... arguments) throws Exception {
      FunctionCall local = binder.searchInstance(scope, object, name, arguments);
      
      if(local == null) {
         Module module = scope.getModule();
         FunctionCall external = binder.searchModule(scope, module, name, arguments); // maybe closure should be first
         
         if(external != null) {
            return external;
         }
         FunctionCall closure = binder.searchScope(object, name, arguments); // closure
         
         if(closure != null) {
            return closure;
         }
      }
      return local;  
   }
}