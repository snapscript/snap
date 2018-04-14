package org.snapscript.core.function.dispatch;

import static org.snapscript.core.error.Reason.INVOKE;
import static org.snapscript.core.type.Phase.COMPILE;
import static org.snapscript.core.type.Phase.EXECUTE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.Reason;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Phase;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class TypeLocalDispatcher implements FunctionDispatcher<Scope> {
   
   private final FunctionResolver binder;
   private final ErrorHandler handler;
   private final String name;
   
   public TypeLocalDispatcher(FunctionResolver binder, ErrorHandler handler, String name) {
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
            handler.handleCompileError(INVOKE, scope, type, name, arguments);
         } else {
            handler.handleCompileError(INVOKE, scope, name, arguments);
         }
      }
      return match.check();   
   }

   @Override
   public Value dispatch(Scope scope, Scope object, Object... arguments) throws Exception {
      FunctionCall match = bind(scope, object, arguments);
      
      if(match == null) {
         Type type = object.getType();
         
         if(type != null) {
            handler.handleRuntimeError(INVOKE, scope, type, name, arguments);
         } else {
            handler.handleRuntimeError(INVOKE, scope, name, arguments);
         }
      }
      return match.call();          
   }
   
   private FunctionCall bind(Scope scope, Type object, Type... arguments) throws Exception {
      Type type = scope.getType();
      FunctionCall local = binder.resolveInstance(scope, type, name, arguments);
      
      if(local == null) {
         Module module = scope.getModule();
         FunctionCall external = binder.resolveModule(scope, module, name, arguments); // maybe closure should be first
         
         if(external != null) {
            return external;
         }
         FunctionCall closure = binder.resolveScope(scope, name, arguments); // closure
         
         if(closure != null) {
            return closure;
         }
      }
      return local;  
   }
   
   private FunctionCall bind(Scope scope, Scope object, Object... arguments) throws Exception {
      FunctionCall local = binder.resolveInstance(scope, scope, name, arguments);
      
      if(local == null) {
         Module module = scope.getModule();
         FunctionCall external = binder.resolveModule(scope, module, name, arguments); // maybe closure should be first
         
         if(external != null) {
            return external;
         }
         FunctionCall closure = binder.resolveScope(scope, name, arguments); // closure
         
         if(closure != null) {
            return closure;
         }
      }
      return local;  
   }
}