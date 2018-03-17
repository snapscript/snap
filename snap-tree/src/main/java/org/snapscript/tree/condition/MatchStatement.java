package org.snapscript.tree.condition;

import static org.snapscript.tree.condition.RelationalOperator.EQUALS;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;

public class MatchStatement implements Compilation {
   
   private final Statement statement;
   
   public MatchStatement(Evaluation evaluation, Case... cases) {
      this.statement = new CompileResult(evaluation, cases);
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

      private final Evaluation condition;
      private final Case[] cases;
      
      public CompileResult(Evaluation condition, Case... cases) {
         this.condition = condition;
         this.cases = cases;
      }
      
      @Override
      public void define(Scope scope) throws Exception {
         for(int i = 0; i < cases.length; i++){
            Statement statement = cases[i].getStatement();
            statement.define(scope);
         }
         condition.define(scope);
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Value left = condition.evaluate(scope, null);
         
         for(int i = 0; i < cases.length; i++){
            Evaluation evaluation = cases[i].getEvaluation();
            
            if(evaluation == null) {
               Statement statement = cases[i].getStatement();
               return statement.execute(scope);
            }
            Value right = evaluation.evaluate(scope, null);
            Value value = EQUALS.operate(scope, left, right);
            boolean match = value.getBoolean();
            
            if(match) {
               Statement statement = cases[i].getStatement();
               
               if(statement != null) {
                  return statement.execute(scope);
               }
               return Result.getNormal();
            }  
         }
         return Result.getNormal();
      }
   }
}