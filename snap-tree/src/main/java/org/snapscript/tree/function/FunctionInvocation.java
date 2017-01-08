package org.snapscript.tree.function;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
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
   
   public FunctionInvocation(Evaluation function, ArgumentList arguments, Evaluation... evaluations) {
      this.invocation = new CompileResult(function, arguments, evaluations);
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
      private final ArgumentList arguments;
      private final Evaluation[] evaluations;
      
      public CompileResult(Evaluation function, ArgumentList arguments, Evaluation... evaluations) {
         this.extractor = new NameExtractor(function);
         this.dispatcher = new InvocationBinder();
         this.evaluations = evaluations;
         this.arguments = arguments;
      }
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         String name = extractor.extract(scope); 
         Value array = arguments.create(scope); 
         Object[] arguments = array.getValue();
         InvocationDispatcher handler = dispatcher.bind(scope, left);
         Value value = handler.dispatch(name, arguments);
         
         for(Evaluation evaluation : evaluations) {
            Object result = value.getValue();
            
            if(result == null) {
               throw new InternalStateException("Result was null"); 
            }
            value = evaluation.evaluate(scope, result);
         }
         return value; 
      }
   }
}