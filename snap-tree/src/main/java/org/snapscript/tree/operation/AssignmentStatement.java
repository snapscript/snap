package org.snapscript.tree.operation;

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
import org.snapscript.parse.StringToken;

public class AssignmentStatement implements Compilation {
   
   private final Statement assignment;
   
   public AssignmentStatement(Evaluation reference, StringToken token, Evaluation value) {
      this.assignment = new CompileResult(reference, token, value);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, assignment, trace);
   }

   private static class CompileResult extends Statement {
      
      private final Evaluation assignment;
      
      public CompileResult(Evaluation left, StringToken token, Evaluation right) {
         this.assignment = new Assignment(left, token, right);
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         assignment.define(scope);
         return false;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         assignment.compile(scope, null);
         return new CompileExecution(assignment);
      }
   }
   
   private static class CompileExecution extends Execution {
      
      private final Evaluation assignment;
      
      public CompileExecution(Evaluation assignment) {
         this.assignment = assignment;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Value reference = assignment.evaluate(scope, null);
         Object value = reference.getValue();
         
         return Result.getNormal(value);
      }
   }
}