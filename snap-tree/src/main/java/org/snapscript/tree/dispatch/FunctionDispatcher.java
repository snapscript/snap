package org.snapscript.tree.dispatch;

import java.util.List;
import java.util.concurrent.Callable;

import org.snapscript.core.Any;
import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.ValueTypeExtractor;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;

public class FunctionDispatcher implements InvocationDispatcher {
   
   private final ObjectDispatcher dispatcher;
   private final FunctionAdapter adapter;
   private final Object function;
   private final Scope scope;      
   
   public FunctionDispatcher(ValueTypeExtractor extractor, Scope scope, Object function) {
      this.adapter = new FunctionAdapter(function);
      this.dispatcher = new ObjectDispatcher(extractor, scope, adapter);
      this.function = function;
      this.scope = scope;
   }

   @Override
   public Value dispatch(String name, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      Callable<Result> call = binder.bind(scope, function, name, arguments); // this is not used often
      
      if(call == null) {
         return dispatcher.dispatch(name, arguments);
      }
      Result result = call.call();
      Object value = result.getValue();

      return ValueType.getTransient(value);
   }
   
   private static class FunctionAdapter implements Any {
      
      private final Function function;
      
      public FunctionAdapter(Object function) {
         this.function = (Function)function;
      }
      
      public int getModifiers() {
         return function.getModifiers();
      }
      
      public Type getType() {
         return function.getType();
      }
      
      public Type getDefinition() {
         return function.getDefinition();
      }
      
      public Type getConstraint() {
         return function.getConstraint();
      }
      
      public String getName() {
         return function.getName();
      }
      
      public Signature getSignature() {
         return function.getSignature();
      }
      
      public List<Annotation> getAnnotations() {
         return function.getAnnotations();
      }
      
      public Invocation getInvocation() {
         return function.getInvocation();
      }
      
      public String getDescription() {
         return function.getDescription();
      }
      
      @Override
      public String toString() {
         return function.toString();
      }
   }
}