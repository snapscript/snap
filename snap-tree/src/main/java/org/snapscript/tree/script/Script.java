package org.snapscript.tree.script;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

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
   public void compile(Scope scope) throws Exception {
      for(Statement statement : statements) {
         statement.compile(scope);
      }
   }
   
   @Override
   public Result execute(Scope scope) throws Exception {
      Result last = Result.getNormal();
      
      for(Statement statement : statements) {
         Result result = statement.execute(scope);
         
         if(!result.isNormal()){
            throw new InternalStateException("Illegal statement");
         }
         last = result;
      }
      return last;
   }
}