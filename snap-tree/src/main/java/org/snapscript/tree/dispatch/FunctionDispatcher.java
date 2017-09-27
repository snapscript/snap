package org.snapscript.tree.dispatch;

import java.util.List;
import java.util.concurrent.Callable;

import org.snapscript.core.Any;
import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.NameReference;

public class FunctionDispatcher implements InvocationDispatcher<Function> {

   private final NameReference reference;      
   
   public FunctionDispatcher(NameReference reference) {
      this.reference = reference;
   }

   @Override
   public Value dispatch(Scope scope, Function function, Object... arguments) throws Exception {
      Callable<Value> call = bind(scope, function, arguments); // this is not used often
      
      if(call == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         ErrorHandler handler = context.getHandler();
         String name = reference.getName(scope);

         handler.throwInternalException(scope, function, name, arguments);
      }
      return call.call();
   }
   
   private Callable<Value> bind(Scope scope, Function function, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      String name = reference.getName(scope);
      Callable<Value> call = binder.bind(scope, function, name, arguments); // this is not used often
      
      if(call == null) {
         Object adapter = new FunctionAdapter(function);
         
         return binder.bind(scope, adapter, name, arguments);
      }
      return call;
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
      
      public Type getHandle() {
         return function.getHandle();
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