package org.snapscript.tree.function;

import static org.snapscript.core.Reserved.METHOD_CLOSURE;

import java.util.concurrent.Callable;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceEvaluation;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceType;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.NameExtractor;
import org.snapscript.tree.dispatch.InvocationBinder;
import org.snapscript.tree.dispatch.InvocationDispatcher;

public class FunctionInvocation implements Compilation {
   
   private final Evaluation invocation;
   
   public FunctionInvocation(Evaluation function, ArgumentList... list) {
      this.invocation = new CompileResult(function, list);
   }
   
   @Override
   public Evaluation compile(Module module, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = TraceType.getInvoke(module, line);
      
      return new TraceEvaluation(interceptor, invocation, trace);
   }
   
   private static class CompileResult implements Evaluation {
   
      private final InvocationBinder dispatcher;
      private final NameExtractor extractor;
      private final ArgumentList[] list;
      
      public CompileResult(Evaluation function, ArgumentList... list) {
         this.extractor = new NameExtractor(function);
         this.dispatcher = new InvocationBinder();
         this.list = list;
      }
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         String name = extractor.extract(scope); 
         Value array = list[0].create(scope); 
         Object[] arguments = array.getValue();
         InvocationDispatcher handler = dispatcher.bind(scope, left);
         Value result = handler.dispatch(name, arguments);
         
         if(list.length > 1) {
            return evaluate(scope, result);
         }
         return result; 
      }
      
      private Value evaluate(Scope scope, Value value) throws Exception { // curry like func(2)("x")
         Module module = scope.getModule();
         Context context = module.getContext();
         FunctionBinder binder = context.getBinder();
         
         for(int i = 1; i < list.length; i++) {
            Value array = list[i].create(scope); 
            Object[] arguments = array.getValue();
            Callable<Result> call = binder.bind(value, arguments);
            int width = arguments.length;
            
            if(call == null) {
               throw new InternalStateException("Result was not a closure of " + width +" arguments");
            }
            Result result = call.call();
            Object object = result.getValue();
            
            value = ValueType.getTransient(object);
         }
         return value; 
      }
   }
}