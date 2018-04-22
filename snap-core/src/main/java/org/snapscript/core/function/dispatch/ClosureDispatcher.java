package org.snapscript.core.function.dispatch;

import static org.snapscript.core.constraint.Constraint.NONE;
import static org.snapscript.core.error.Reason.INVOKE;

import java.util.List;

import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Any;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class ClosureDispatcher implements FunctionDispatcher<Function> {

   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;      
   
   public ClosureDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.handler = handler;
      this.resolver = resolver;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint function, Type... arguments) throws Exception { 
      return NONE;
   }

   @Override
   public Value dispatch(Scope scope, Function function, Object... arguments) throws Exception {
      FunctionCall call = bind(scope, function, arguments); // this is not used often
      
      if(call == null) {
         handler.handleRuntimeError(INVOKE, scope, function, name, arguments);
      }
      return call.call();
   }
   
   private FunctionCall bind(Scope scope, Function function, Object... arguments) throws Exception {
      FunctionCall call = resolver.resolveInstance(scope, function, name, arguments); // this is not used often
      
      if(call == null) {
         Object adapter = new FunctionAdapter(function);
         
         return resolver.resolveInstance(scope, adapter, name, arguments);
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
      
      public Constraint getConstraint() {
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