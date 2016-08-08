package org.snapscript.tree.condition;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.core.trace.TraceType;
import org.snapscript.tree.NameExtractor;
import org.snapscript.tree.collection.Iteration;
import org.snapscript.tree.collection.IterationConverter;

public class ForInStatement implements Compilation {
   
   private final Statement loop;
   
   public ForInStatement(Evaluation identifier, Evaluation collection, Statement body) {
      this.loop = new CompileResult(identifier, collection, body);
   }
   
   @Override
   public Statement compile(Module module, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = TraceType.getNormal(module, line);
      
      return new TraceStatement(interceptor, handler, loop, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final IterationConverter converter;
      private final NameExtractor extractor;
      private final Evaluation collection;
      private final Statement body;
   
      public CompileResult(Evaluation identifier, Evaluation collection, Statement body) {
         this.extractor = new NameExtractor(identifier);
         this.converter = new IterationConverter();
         this.collection = collection;
         this.body = body;
      }
      
      @Override
      public Result compile(Scope scope) throws Exception {   
         return body.compile(scope);
      }
   
      @Override
      public Result execute(Scope scope) throws Exception { 
         Value list = collection.evaluate(scope, null);
         String name = extractor.extract(scope);
         Object object = list.getValue();
         Iteration iteration = converter.convert(scope, object);
         Iterable iterable = iteration.getIterable(scope);
         State state = scope.getState();
         
         for (Object entry : iterable) {
            Value variable = state.getValue(name);
            
            if(variable == null) {
               Value value = ValueType.getReference(entry);
               state.addVariable(name, value);
            } else {
               variable.setValue(entry);
            }
            Result result = body.execute(scope);   
   
            if (result.isReturn()) {
               return result;
            }
            if (result.isBreak()) {
               return ResultType.getNormal();
            }
         }    
         return ResultType.getNormal();
      }
   }
}