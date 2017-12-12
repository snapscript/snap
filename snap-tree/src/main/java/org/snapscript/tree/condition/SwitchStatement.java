package org.snapscript.tree.condition;

import static org.snapscript.tree.condition.RelationalOperator.EQUALS;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Result;
import org.snapscript.core.Resume;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;
import org.snapscript.core.Yield;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.tree.SuspendStatement;

public class SwitchStatement implements Compilation {
   
   private final Statement statement;
   
   public SwitchStatement(Evaluation evaluation, Case... cases) {
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
   
   private static class CompileResult extends SuspendStatement<Integer> {

      private final Evaluation condition;
      private final Result normal;
      private final Case[] cases;
      
      public CompileResult(Evaluation condition, Case... cases) {
         this.normal = Result.getNormal();
         this.condition = condition;
         this.cases = cases;
      }
      
      @Override
      public Result compile(Scope scope) throws Exception {
         Result last = Result.getNormal();
         
         for(int i = 0; i < cases.length; i++){
            Statement statement = cases[i].getStatement();
            Result result = statement.compile(scope);
            
            if(!result.isNormal()){
               return result;
            }
            last = result;
         }
         condition.compile(scope);
         return last;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Value left = condition.evaluate(scope, null);
         
         for(int i = 0; i < cases.length; i++){
            Evaluation evaluation = cases[i].getEvaluation();
            
            if(evaluation == null) {
               Statement statement = cases[i].getStatement();
               Result result = statement.execute(scope);
               
               if(result.isBreak()) {
                  return normal;
               }
               if(!result.isNormal()) {
                  return result;      
               }
               return normal;
            }
            Value right = evaluation.evaluate(scope, null);
            Value value = EQUALS.operate(scope, left, right);
            boolean match = value.getBoolean();
            
            if(match) {
               return resume(scope, i);
            }  
         }
         return normal;
      }
      
      @Override
      public Result resume(Scope scope, Integer index) throws Exception {
         for(int j = index; j < cases.length; j++) {
            Statement statement = cases[j].getStatement();
            Result result = statement.execute(scope);

            if(result.isYield()) {
               return suspend(scope, result, this, j);
            }
            if(result.isBreak()) {
               return normal;
            }
            if(!result.isNormal()) {
               return result;      
            }
         }   
         return normal;
      }

      @Override
      public Resume create(Result result, Resume resume, Integer value) throws Exception {
         Yield yield = result.getValue();
         Resume child = yield.getResume();
         
         return new SwitchResume(child, resume, value);
      }
   }
}