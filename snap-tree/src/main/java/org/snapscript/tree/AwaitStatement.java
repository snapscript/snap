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
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.core.variable.Value;
import org.snapscript.core.resume.Resume;
import org.snapscript.core.resume.Yield;

public class AwaitStatement implements Compilation {

   private final Statement control;

   public AwaitStatement(Evaluation right){
      this(null, right);
   }

   public AwaitStatement(Evaluation left, Evaluation right){
      this.control = new CompileResult(left, right);
   }

   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);

      return new TraceStatement(interceptor, handler, control, trace);
   }

   private static class CompileResult extends Statement {

      private final Evaluation right;
      private final Evaluation left;

      public CompileResult(Evaluation left, Evaluation right){
         this.right = right;
         this.left = left;
      }

      @Override
      public boolean define(Scope scope) throws Exception {
         if(left != null) {
            left.define(scope);
         }
         if(right != null) {
            right.define(scope);
         }
         return true;
      }

      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         if(left != null) {
            left.compile(scope, null);
         }
         if(right != null) {
            right.compile(scope, null);
         }
         return new CompileExecution(left, right);
      }
   }

   private static class CompileExecution extends SuspendStatement<Object> {

      private final Evaluation right;
      private final Evaluation left;

      public CompileExecution(Evaluation left, Evaluation right){
         this.right = new AwaitExpression(right);
         this.left = left;
      }

      @Override
      public Result execute(Scope scope) throws Exception {
         Result result = Result.getAwait(null, scope, this);
         Yield value = result.getValue();

         return suspend(scope, result, this, null);
      }

      @Override
      public Result resume(Scope scope, Object data) throws Exception {
         Value value = right.evaluate(scope, null);
         Object object = value.getValue();

         if(left != null) {
            Value reference = left.evaluate(scope, null);

            if(reference != null) {
               reference.setValue(object);
            }
         }
         return Result.getNormal(object);
      }

      @Override
      public Resume suspend(Result result, Resume resume, Object value) throws Exception {
         return new AwaitResume(resume);
      }
   }
}