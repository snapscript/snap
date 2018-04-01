package org.snapscript.core.function.dispatch;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.search.FunctionCall;
import org.snapscript.core.function.search.FunctionSearcher;

public class TypeLocalDispatcher implements FunctionDispatcher<Scope> {
   
   private final FunctionSearcher binder;
   private final ErrorHandler handler;
   private final String name;
   
   public TypeLocalDispatcher(FunctionSearcher binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Type object, Type... arguments) throws Exception {
      FunctionCall match = bind(scope, object, arguments);
      
      if(match == null) {
         Type type = scope.getType();
         
         if(type != null) {
            handler.handleCompileError(scope, type, name, arguments);
         }
         handler.handleCompileError(scope, name, arguments);
      }
      return match.check();   
   }

   @Override
   public Value dispatch(Scope scope, Scope object, Object... arguments) throws Exception {
      FunctionCall match = bind(scope, object, arguments);
      
      if(match == null) {
         Type type = object.getType();
         
         if(type != null) {
            handler.handleRuntimeError(scope, type, name, arguments);
         }
         handler.handleRuntimeError(scope, name, arguments);
      }
      return match.call();          
   }
   
   private FunctionCall bind(Scope scope, Type object, Type... arguments) throws Exception {
      Type type = scope.getType();
      FunctionCall local = binder.searchInstance(scope, type, name, arguments);
      
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
      FunctionCall local = binder.searchInstance(scope, scope, name, arguments);
      
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
}