package org.snapscript.tree.condition;

import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Local;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.collection.Iteration;
import org.snapscript.tree.collection.IterationConverter;

public class ForInStatement implements Compilation {
   
   private final Statement loop;
   
   public ForInStatement(Evaluation identifier, Evaluation collection, Statement body) {
      this.loop = new CompileResult(identifier, collection, body);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, loop, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final IterationConverter converter;
      private final NameReference reference;
      private final Evaluation collection;
      private final AtomicInteger index;
      private final Statement body;
   
      public CompileResult(Evaluation identifier, Evaluation collection, Statement body) {
         this.reference = new NameReference(identifier);
         this.converter = new IterationConverter();
         this.index = new AtomicInteger();
         this.collection = collection;
         this.body = body;
      }
      
      @Override
      public Result compile(Scope scope) throws Exception { 
         String name = reference.getName(scope);
         State state = scope.getState();
         int depth = state.addLocal(name);
         
         collection.compile(scope);
         index.set(depth);
         
         return body.compile(scope);
      }
   
      @Override
      public Result execute(Scope scope) throws Exception { 
         Value list = collection.evaluate(scope, null);
         Object object = list.getValue();
         Iteration iteration = converter.convert(scope, object);
         Iterable iterable = iteration.getIterable(scope);
         
         return execute(scope, iterable);
      }

      private Result execute(Scope scope, Iterable iterable) throws Exception {
         String name = reference.getName(scope);
         State state = scope.getState();
         Local local = Local.getReference(name, name);
         int depth = index.get();
         
         state.addLocal(depth, local);
         
         for (Object entry : iterable) {
            local.setValue(entry);
            
            Result result = body.execute(scope);   
   
            if (result.isReturn()) {
               return result;
            }
            if (result.isBreak()) {
               return Result.getNormal();
            }
         }    
         return Result.getNormal();
      }
   }
}