package org.snapscript.core.function.dispatch;

import static org.snapscript.core.error.Reason.INVOKE;

import java.util.List;

import org.snapscript.core.array.ArrayBuilder;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.Connection;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class ArrayDispatcher implements FunctionDispatcher {
   
   private final FunctionResolver resolver;
   private final ArrayBuilder builder;
   private final ErrorHandler handler;
   private final String name;
   
   public ArrayDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.builder = new ArrayBuilder();
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      Type actual = constraint.getType(scope);
      Type list = builder.convert(actual);
      FunctionCall call = resolver.resolveInstance(scope, list, name, arguments);
      
      if(call == null) {
         handler.handleCompileError(INVOKE, scope, actual, name, arguments);
      }
      return call.check(scope, constraint, arguments);
   }

   @Override
   public Connection dispatch(Scope scope, Value value, Object... arguments) throws Exception {
      Object object = value.getValue();
      List list = builder.convert(object);
      FunctionCall call = resolver.resolveInstance(scope, list, name, arguments);
      
      if(call == null) {
         handler.handleRuntimeError(INVOKE, scope, object, name, arguments);
      }
      return new ArrayConnection(call, builder);
   }
   
   private static class ArrayConnection implements Connection<Value> {
      
      private final ArrayBuilder builder;
      private final FunctionCall call;
      
      public ArrayConnection(FunctionCall call, ArrayBuilder builder) {
         this.builder = builder;
         this.call = call;
      }

      @Override
      public Object invoke(Scope scope, Value value, Object... arguments) throws Exception {
         Object source = value.getValue();
         List list = builder.convert(source);
         
         return call.invoke(scope, list, arguments);
      }

      @Override
      public boolean match(Scope scope, Object object, Object... arguments) throws Exception {
         return call.match(scope, object, arguments);
      }
      
   }
}