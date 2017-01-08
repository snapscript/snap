package org.snapscript.tree.closure;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.closure.ClosureScopeExtractor;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceEvaluation;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceType;
import org.snapscript.tree.Expression;

public class Closure implements Compilation {
   
   private final ClosureParameterList parameters;
   private final Statement statement;
   private final Evaluation expression;
   
   public Closure(ClosureParameterList parameters, Statement statement){  
      this(parameters, statement, null);
   }  
   
   public Closure(ClosureParameterList parameters, Expression expression){
      this(parameters, null, expression);
   }
   
   public Closure(ClosureParameterList parameters, Statement statement, Expression expression){
      this.parameters = parameters;
      this.expression = expression;
      this.statement = statement;
   }
   
   @Override
   public Evaluation compile(Module module, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = TraceType.getNormal(module, line);
      Evaluation evaluation = expression;
      
      if(evaluation != null) {
         evaluation = new TraceEvaluation(interceptor, evaluation, trace);
      }
      return new CompileResult(parameters, statement, evaluation);
   }
  
   private static class CompileResult implements Evaluation {
   
      private final ClosureScopeExtractor extractor;
      private final ClosureParameterList parameters;
      private final ClosureBuilder builder;
      private final AtomicBoolean compile;
      private final Statement closure;

      public CompileResult(ClosureParameterList parameters, Statement statement, Evaluation expression){
         this.closure = new ClosureStatement(statement, expression);
         this.extractor = new ClosureScopeExtractor();
         this.builder = new ClosureBuilder(closure);
         this.compile = new AtomicBoolean();
         this.parameters = parameters;
      }
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         Signature signature = parameters.create(scope);
         Scope capture = extractor.extract(scope);
         Function function = builder.create(signature, capture); // creating new function each time
         
         if(compile.compareAndSet(false, true)) {
            closure.compile(capture);
         }
         return ValueType.getTransient(function);
      }
   }
}