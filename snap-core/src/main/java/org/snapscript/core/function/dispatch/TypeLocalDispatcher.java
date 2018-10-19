package org.snapscript.core.function.dispatch;

import static org.snapscript.core.error.Reason.INVOKE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.dispatch.FunctionDispatcher.Call2;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class TypeLocalDispatcher implements FunctionDispatcher {
   
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;
   
   public TypeLocalDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      Type object = constraint.getType(scope);
      FunctionCall match = bind(scope, object, arguments);
      
      if(match == null) {
         Type type = scope.getType();
         
         if(type != null) {
            handler.handleCompileError(INVOKE, scope, type, name, arguments);
         } else {
            handler.handleCompileError(INVOKE, scope, name, arguments);
         }
      }
      return match.check(constraint, arguments);
   }

   @Override
   public Call2 dispatch(Scope scope, Value value, Object... arguments) throws Exception {
      Scope object = value.getValue();
      Call2 call = bind(scope, object, arguments);
      
      if(call == null) {
         Type type = scope.getType();
         
         if(type != null) {
            handler.handleRuntimeError(INVOKE, scope, type, name, arguments);
         } else {
            handler.handleRuntimeError(INVOKE, scope, name, arguments);
         }
      }
      return call;     
   }
   
   private FunctionCall bind(Scope scope, Type object, Type... arguments) throws Exception {
      Type type = scope.getType();
      FunctionCall local = resolver.resolveInstance(scope, type, name, arguments);
      
      if(local == null) {
         Module module = scope.getModule();
         FunctionCall external = resolver.resolveModule(scope, module, name, arguments); // maybe closure should be first
         
         if(external != null) {
            return external;
         }
         FunctionCall closure = resolver.resolveScope(scope, name, arguments); // closure
         
         if(closure != null) {
            return closure;
         }
      }
      return local;  
   }
   
   private Call2 bind(Scope scope, Scope object, Object... arguments) throws Exception {
      final FunctionCall local = resolver.resolveInstance(scope, scope, name, arguments);
      
      if(local == null) {
         final Module module = scope.getModule();
         final FunctionCall external = resolver.resolveModule(scope, module, name, arguments); // maybe closure should be first
         
         if(external != null) {
            return new Call2(external) {
               
               public Object invoke(Scope scope, Object source, Object... arguments) throws Exception{
                  return call.invoke(scope, module, arguments);
               }
            };
         }
         final FunctionCall closure = resolver.resolveScope(scope, name, arguments); // closure
         
         if(closure != null) {
            return new Call2(closure) {
               
               public Object invoke(Scope scope, Object source, Object... arguments) throws Exception{
                  return call.invoke(scope, scope, arguments);
               }
            }; 
         }
         return null;
      }
      return new Call2(local) {
         
         public Object invoke(Scope scope, Object source, Object... arguments) throws Exception{
            return call.invoke(scope, scope, arguments);
         }
      };  
   }
}