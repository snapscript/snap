package org.snapscript.core.function.dispatch;

import static org.snapscript.core.constraint.Constraint.LIST;
import static org.snapscript.core.error.Reason.INVOKE;

import java.util.List;

import org.snapscript.core.array.ArrayBuilder;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.module.Module;
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
      return call.check(constraint);
   }

   @Override
   public Value dispatch(Scope scope, Value value, Object... arguments) throws Exception {
      Object object = value.getValue();
      Module module = scope.getModule();
      List list = builder.convert(object);
      Value array = Value.getTransient(list, module, LIST);
      FunctionCall call = resolver.resolveInstance(scope, array, name, arguments);
      
      if(call == null) {
         handler.handleRuntimeError(INVOKE, scope, object, name, arguments);
      }
      return call.call();
   }
}