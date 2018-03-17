package org.snapscript.tree.script;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class ScriptPackage extends Statement {

   private final Statement[] statements;
   private final AtomicBoolean execute;
   private final AtomicBoolean compile;
   private final AtomicBoolean define;
   
   public ScriptPackage(Statement... statements) {
      this.execute = new AtomicBoolean(true);
      this.compile = new AtomicBoolean(true);
      this.define = new AtomicBoolean(true);
      this.statements = statements;
   }
   
   @Override
   public void create(Scope scope) throws Exception {
      if(define.compareAndSet(true, false)) {
         for(Statement statement : statements) {
            statement.create(scope);
         }
      }
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      if(compile.compareAndSet(true, false)) {
         for(Statement statement : statements) {
            statement.define(scope);
         }
      }
   }
   
   @Override
   public Result execute(Scope scope) throws Exception {
      Result last = Result.getNormal();
      
      if(execute.compareAndSet(true, false)) {
         for(int i = 0; i < statements.length; i++) {
            Result result = statements[i].execute(scope);
            
            if(!result.isNormal()){
               throw new InternalStateException("Illegal statement");
            }
            statements[i] = null;
            last = result;
         }
      }
      return last;
   }
}