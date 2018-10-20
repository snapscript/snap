package org.snapscript.core.function.dispatch;

import static org.snapscript.core.error.Reason.INVOKE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.Connection;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionConnection;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class TypeStaticDispatcher implements FunctionDispatcher {
   
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;
   
   public TypeStaticDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      Type type = constraint.getType(scope);
      FunctionCall call = resolver.resolveStatic(scope, type, name, arguments);

      if(call == null) {
         handler.handleCompileError(INVOKE, scope, type, name, arguments);
      }
      return call.check(scope, constraint, arguments);
   } 

   @Override
   public Connection dispatch(Scope scope, Value value, Object... arguments) throws Exception { 
      Type type = value.getValue();
      FunctionCall call = resolver.resolveStatic(scope, type, name, arguments);

      if(call == null) {
         FunctionCall instance = resolver.resolveInstance(scope, (Object)type, name, arguments); // find on the type
      
         if(instance == null) {
            handler.handleRuntimeError(INVOKE, scope, type, name, arguments);
         }
         return new FunctionConnection(instance);   
      }
      return new TypeStaticConnection(call); 
   } 
   
   private static class TypeStaticConnection implements Connection {

      private final FunctionCall call;
      
      public TypeStaticConnection(FunctionCall call) {
         this.call = call;
      }
      
      @Override
      public boolean match(Scope scope, Object object, Object... arguments) throws Exception {
         return call.match(scope, object, arguments);
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... arguments) throws Exception {
         return call.invoke(scope, null, arguments);
      } 
   }
}