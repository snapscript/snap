package org.snapscript.tree;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class StatementBlock extends Statement {
   
   private volatile StatementCompiler compiler;
   private volatile StatementExecutor executor;
   
   public StatementBlock(Statement... statements) {
      this.compiler = new StatementCompiler(statements);
   }
   
   @Override
   public Result compile(Scope scope) throws Exception {
      if(executor == null) {
         executor = compiler.compile(scope);
      }
      return Result.getNormal();
   }
   
   @Override
   public Result execute(Scope scope) throws Exception {
      if(executor == null) {
         throw new InternalStateException("Statement was not compiled");
      }
      return executor.execute(scope);
   }
   
   private static class StatementCompiler {
      
      private final Statement[] statements;
      
      public StatementCompiler(Statement[] statements) {
         this.statements = statements;
      }
      
      public StatementExecutor compile(Scope scope) throws Exception {
         for(Statement statement : statements) {
            statement.compile(scope);
         }
         return new StatementExecutor(statements);
      }
   }
   
   private static class StatementExecutor {
      
      private final Statement[] statements;
      private final Result normal;
      
      public StatementExecutor(Statement[] statements) {
         this.normal = Result.getNormal();
         this.statements = statements;
      }
      
      public Result execute(Scope scope) throws Exception {
         Result last = normal;

         for(Statement statement : statements) {
            Result result = statement.execute(scope);
            
            if(!result.isNormal()){
               return result;
            }
            last = result;
         }
         return last;
      }
   }
}
