package org.snapscript.tree;

import org.snapscript.core.Index;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.yield.Resume;
import org.snapscript.core.yield.Yield;

public class StatementBlock extends Statement {
   
   private volatile StatementBuilder compiler;
   private volatile StatementExecutor executor;
   
   public StatementBlock(Statement... statements) {
      this.compiler = new StatementBuilder(statements);
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      if(executor == null) {
         executor = compiler.create(scope);
      }
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      if(executor == null) {
         throw new InternalStateException("Statement was not compiled");
      }
      executor.compile(scope);
   }
   
   @Override
   public Result execute(Scope scope) throws Exception {
      if(executor == null) {
         throw new InternalStateException("Statement was not compiled");
      }
      return executor.execute(scope);
   }
   
   private static class StatementBuilder {
      
      private final Statement[] statements;
      
      public StatementBuilder(Statement[] statements) {
         this.statements = statements;
      }
      
      public StatementExecutor create(Scope scope) throws Exception {
         Index index = scope.getIndex();
         int size = index.size();
         
         try {
            for(Statement statement : statements) {
               statement.define(scope);
            }
         } finally {
            index.reset(size);
         }
         return new StatementExecutor(statements);
      }
   }
   
   private static class StatementExecutor extends SuspendStatement<Integer> {
      
      private final Statement[] statements;
      private final Result normal;
      
      public StatementExecutor(Statement[] statements) {
         this.normal = Result.getNormal();
         this.statements = statements;
      }
      
      @Override
      public void compile(Scope scope) throws Exception {
         for(Statement statement : statements) {
            statement.compile(scope);
         }
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         return resume(scope, 0);
      }
      
      @Override
      public Result resume(Scope scope, Integer index) throws Exception {
         Result last = normal;

         for(int i = index; i < statements.length; i++){
            Statement statement = statements[i];
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