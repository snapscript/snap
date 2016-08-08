package org.snapscript.tree.function;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
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
   
   public FunctionInvocation(Evaluation function) {
      this(function, null);
   }
   
   public FunctionInvocation(Evaluation function, ArgumentList list) {
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
      private final ArgumentList list;
      
      public CompileResult(Evaluation function, ArgumentList list) {
         this.extractor = new NameExtractor(function);
         this.dispatcher = new InvocationBinder();
         this.list = list;
      }
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         InvocationDispatcher handler = dispatcher.bind(scope, left);
         String name = extractor.extract(scope);      
         
         if(list != null) {
            Value array = list.evaluate(scope, null); // arguments have no left hand side
            Object[] arguments = array.getValue();
            
            return handler.dispatch(name, arguments);
         }
         return handler.dispatch(name); 
      }
   }
}