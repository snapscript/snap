package org.snapscript.tree.operation;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.core.trace.TraceType;
import org.snapscript.parse.StringToken;

public class AssignmentStatement implements Compilation {
   
   private final Statement assignment;
   
   public AssignmentStatement(Evaluation reference, StringToken token, Evaluation value) {
      this.assignment = new CompileResult(reference, token, value);
   }
   
   @Override
   public Statement compile(Module module, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = TraceType.getNormal(module, line);
      
      return new TraceStatement(interceptor, handler, assignment, trace);
   }

   private static class CompileResult extends Statement {
      
      private final Evaluation assignment;
      
      public CompileResult(Evaluation left, StringToken token, Evaluation right) {
         this.assignment = new Assignment(left, token, right);
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Value reference = assignment.evaluate(scope, null);
         Object value = reference.getValue();
         
         return ResultType.getNormal(value);
      }
   }
}