package org.snapscript.tree;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.core.trace.TraceType;

public class CompoundStatement implements Compilation {
   
   private final Statement body;
   
   public CompoundStatement(Statement... statements) {
      this.body = new CompileResult(statements);
   }

   @Override
   public Statement compile(Module module, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = TraceType.getScope(module, line); // adds visible scope to stack
      
      return new TraceStatement(interceptor, handler, body, trace);
   }
   
   private static class CompileResult extends Statement {

      private final Statement[] statements;
      private final AtomicBoolean compile;
      private final AtomicInteger depth;
      
      public CompileResult(Statement... statements) {
         this.compile = new AtomicBoolean();
         this.depth = new AtomicInteger();
         this.statements = statements;
      }
      
      @Override
      public Result compile(Scope scope) throws Exception {
         Result last = ResultType.getNormal();
         
         if(compile.compareAndSet(false, true)) {
            for(Statement statement : statements) {
               Result result = statement.compile(scope);
               
               if(result.isDeclare()){
                  depth.getAndIncrement();
               }
            }
         }
         return last;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Result last = ResultType.getNormal();
         
         if(!compile.get()) {
            throw new InternalStateException("Statement was not compiled");
         }
         Scope compound = create(scope);
         
         for(Statement statement : statements) {
            Result result = statement.execute(compound);
            
            if(!result.isNormal()){
               return result;
            }
            last = result;
         }
         return last;
      }
      
      private Scope create(Scope scope) throws Exception {
         int count = depth.get();
         
         if(count > 0) {
            return scope.getInner();
         }
         return scope;
      }
   }
}