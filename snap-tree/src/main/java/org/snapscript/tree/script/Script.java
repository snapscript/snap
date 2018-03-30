package org.snapscript.tree.script;

import static org.snapscript.core.result.Result.NORMAL;

import org.snapscript.core.Execution;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.result.Result;

public class Script extends Statement {
   
   private final Statement[] statements;
   
   public Script(Statement... statements) {
      this.statements = statements;
   }
   
   @Override
   public void create(Scope scope) throws Exception {
      for(Statement statement : statements) {
         statement.create(scope);
      }
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      for(Statement statement : statements) {
         statement.define(scope);
      }
   }
   
   @Override
   public Execution compile(Scope scope) throws Exception {
      Execution[] list = new Execution[statements.length];
      
      for(int i = 0; i < statements.length; i++){
         list[i] = statements[i].compile(scope);
      }
      return new ScriptExecution(list);
   }
   
   private static class ScriptExecution extends Execution {
      
      private final Execution[] statements;
      
      public ScriptExecution(Execution... statements) {
         this.statements = statements;
      }
   
      @Override
      public Result execute(Scope scope) throws Exception {
         Result last = NORMAL;
         
         for(Execution statement : statements) {
            Result result = statement.execute(scope);
            
            if(!result.isNormal()){
               throw new InternalStateException("Illegal statement");
            }
            last = result;
         }
         return last;
      }
   }
}