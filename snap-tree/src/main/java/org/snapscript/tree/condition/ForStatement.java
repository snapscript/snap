package org.snapscript.tree.condition;

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

public class ForStatement implements Compilation {
   
   private final Statement loop;
   
   public ForStatement(Statement declaration, Evaluation evaluation, Statement statement) {
      this(declaration, evaluation, null, statement);
   }
   
   public ForStatement(Statement declaration, Evaluation evaluation, Evaluation assignment, Statement statement) {
      this.loop = new CompileResult(declaration, evaluation, assignment, statement);
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

      private final Evaluation condition;
      private final Statement declaration;
      private final Evaluation assignment;
      private final Statement body;

      public CompileResult(Statement declaration, Evaluation condition, Evaluation assignment, Statement body) {
         this.declaration = declaration;
         this.assignment = assignment;
         this.condition = condition;
         this.body = body;
      }
      
      @Override
      public Result compile(Scope scope) throws Exception {   
         return body.compile(scope);
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Scope compound = scope.getInner();
         
         declaration.execute(compound);
         
         while(true) {
            Value result = condition.evaluate(compound, null);
            Boolean value = result.getBoolean();
            
            if(value.booleanValue()) {
               Result next = body.execute(compound);
               
               if(next.isReturn()) {
                  return next;
               }
               if(next.isBreak()) {
                  return ResultType.getNormal();
               }
            } else {
               return ResultType.getNormal();
            } 
            if(assignment != null) {
               assignment.evaluate(compound, null);
            }
         }
      }
   }
}