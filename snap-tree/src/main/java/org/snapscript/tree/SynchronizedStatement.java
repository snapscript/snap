package org.snapscript.tree;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Execution;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.core.variable.Value;
import org.snapscript.core.yield.Resume;
import org.snapscript.core.yield.Yield;

public class SynchronizedStatement implements Compilation {
   
   private final Statement statement;
   
   public SynchronizedStatement(Evaluation evaluation, Statement statement) {
      this.statement = new CompileResult(evaluation, statement);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, statement, trace);
   }
   
   private static class CompileResult extends Statement {

      private final Statement statement;
      private final Evaluation reference;
      
      public CompileResult(Evaluation reference, Statement statement) {
         this.statement = statement;
         this.reference = reference;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         reference.define(scope);
         statement.define(scope);
         return true;
      }

      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         Constraint constraint = reference.compile(scope, null);
         Execution execution = statement.compile(scope, returns);
         
         return new CompileExecution(reference, execution);
      }
   }
   
   private static class CompileExecution extends SuspendStatement<Resume> {

      private final StatementResume statement;
      private final Evaluation reference;
      
      public CompileExecution(Evaluation reference, Execution statement) {
         this.statement = new StatementResume(statement);
         this.reference = reference;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         return resume(scope, statement);
      }
      
      @Override
      public Result resume(Scope scope, Resume statement) throws Exception {
         Object object = resolve(scope);
         
         synchronized(object) {
            Result result = statement.resume(scope, null);
            
            if(result.isYield()) {
               return suspend(scope, result, this, null);
            }
            return result;
         }
      }

      @Override
      public Resume suspend(Result result, Resume resume, Resume value) throws Exception {
         Yield yield = result.getValue();
         Resume child = yield.getResume();
         
         return new SynchronizedResume(this, child);
      }   
      
      private Object resolve(Scope scope) throws Exception {
         Value value = reference.evaluate(scope, null);
         Object object = value.getValue();
         
         if(Instance.class.isInstance(object)) {
            Instance instance = (Instance)object;
            Object bridge = instance.getBridge();
            
            if(bridge != null) {
               return bridge;
            }
         }
         return object;
      }   
   }
}