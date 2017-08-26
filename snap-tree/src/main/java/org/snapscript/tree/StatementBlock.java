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
      return ResultType.getNormal();
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
         int count = 0;
         
         for(Statement statement : statements) {
            Result result = statement.compile(scope);
            
            if(result.isDeclare()){
               count++;
            }
         }
         return new StatementExecutor(statements, count);
      }
   }
   
   private static class StatementExecutor {
      
      private final Statement[] statements;
      private final Result normal;
      private final int depth;
      
      public StatementExecutor(Statement[] statements, int depth) {
         this.normal = ResultType.getNormal();
         this.statements = statements;
         this.depth = depth;
      }
      
      public Result execute(Scope scope) throws Exception {
         Scope compound = scope;
         Result last = normal;
         
         if(depth > 0) {
            compound = compound.getInner();
         }
         for(Statement statement : statements) {
            Result result = statement.execute(compound);
            
            if(!result.isNormal()){
               return result;
            }
            last = result;
         }
         return last;
      }
   }
}
