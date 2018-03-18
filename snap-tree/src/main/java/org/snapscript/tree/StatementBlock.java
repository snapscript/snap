package org.snapscript.tree;

import org.snapscript.core.Execution;
import org.snapscript.core.Index;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.yield.Resume;
import org.snapscript.core.yield.Yield;

public class StatementBlock extends Statement {
   
   private volatile StatementBuilder builder;
   private volatile StatementCompiler compiler;
   
   public StatementBlock(Statement... statements) {
      this.builder = new StatementBuilder(statements);
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      if(compiler == null) {
         compiler = builder.create(scope);
      }
   }
   
   @Override
   public Execution compile(Scope scope) throws Exception {
      if(compiler == null) {
         throw new InternalStateException("Statement was not created");
      }
      return compiler.compile(scope);
   }
   
   private static class StatementBuilder {
      
      private final Statement[] statements;
      
      public StatementBuilder(Statement[] statements) {
         this.statements = statements;
      }
      
      public StatementCompiler create(Scope scope) throws Exception {
         Index index = scope.getIndex();
         int size = index.size();
         
         try {
            for(Statement statement : statements) {
               statement.define(scope);
            }
         } finally {
            index.reset(size);
         }
         return new StatementCompiler(statements);
      }
   }
   
   private static class StatementCompiler {
      
      private final Statement[] statements;
      private final Execution[] executions;
      
      public StatementCompiler(Statement[] statements) {
         this.executions = new Execution[statements.length];
         this.statements = statements;
      }
      
      public Execution compile(Scope scope) throws Exception {
         for(int i = 0; i < statements.length; i++) {
            executions[i]  = statements[i].compile(scope);
         }
         return new StatementExecutor(executions);
      }
   }
   
   private static class StatementExecutor extends SuspendStatement<Integer> {
      
      private final Execution[] statements;
      private final Result normal;
      
      public StatementExecutor(Execution[] statements) {
         this.normal = Result.getNormal();
         this.statements = statements;
      }
      
      
      @Override
      public Result execute(Scope scope) throws Exception {
         return resume(scope, 0);
      }
      
      @Override
      public Result resume(Scope scope, Integer index) throws Exception {
         Result last = normal;

         for(int i = index; i < statements.length; i++){
            Execution statement = statements[i];
            Result result = statement.execute(scope);
            
            if(result.isYield()) {
               return suspend(scope, result, this, i + 1);
            }
            if(!result.isNormal()){
               return result;
            }
            last = result;
         }
         return last;
      }
      
      @Override
      public Resume suspend(Result result, Resume resume, Integer value) throws Exception {
         Yield yield = result.getValue();
         Resume child = yield.getResume();
         
         return new CompoundResume(child, resume, value);
      }
   }
}