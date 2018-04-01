package org.snapscript.core.function.dispatch;

import java.util.List;

import org.snapscript.core.Any;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.search.FunctionCall;
import org.snapscript.core.function.search.FunctionSearcher;

public class ClosureDispatcher implements FunctionDispatcher<Function> {

   private final FunctionSearcher binder;
   private final ErrorHandler handler;
   private final String name;      
   
   public ClosureDispatcher(FunctionSearcher binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Type function, Type... arguments) throws Exception { 
      return Constraint.NONE;
   }

   @Override
   public Value dispatch(Scope scope, Function function, Object... arguments) throws Exception {
      FunctionCall call = bind(scope, function, arguments); // this is not used often
      
      if(call == null) {
         handler.handleRuntimeError(scope, function, name, arguments);
      }
      return call.call();
   }
   
   private FunctionCall bind(Scope scope, Function function, Object... arguments) throws Exception {
      FunctionCall call = binder.searchInstance(scope, function, name, arguments); // this is not used often
      
      if(call == null) {
         Object adapter = new FunctionAdapter(function);
         
         return binder.searchInstance(scope, adapter, name, arguments);
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