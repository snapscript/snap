package org.snapscript.tree.define;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class ModuleBody extends Statement {

   private final Statement[] statements;
   private final ModulePart[] parts;
   private final AtomicBoolean execute;
   private final AtomicBoolean compile;
   private final AtomicBoolean define;
   
   public ModuleBody(ModulePart... parts) {
      this.statements = new Statement[parts.length];
      this.execute = new AtomicBoolean(true);
      this.compile = new AtomicBoolean(true);
      this.define = new AtomicBoolean(true);
      this.parts = parts;
   }
   
   @Override
   public Result define(Scope scope) throws Exception {
      Result last = Result.getNormal();
      
      if(define.compareAndSet(true, false)) {
         for(int i = 0; i < parts.length; i++) {
            statements[i] = parts[i].define(this);
            statements[i].define(scope);
            parts[i] = null;
         }
      }
      return last;
   }
   
   @Override
   public Result compile(Scope scope) throws Exception {
      Result last = Result.getNormal();
      
      if(compile.compareAndSet(true, false)) {
         for(int i = 0; i < parts.length; i++) {
            Result result = statements[i].compile(scope);
            
            if(!result.isNormal()){
               return result;
            }
            last = result;
         }
      }
      return last;
   }
   
   @Override
   public Result execute(Scope scope) throws Exception {
      Result last = Result.getNormal();
      
      if(execute.compareAndSet(true, false)) {
         for(int i = 0; i < parts.length; i++) {
            Result result = statements[i].execute(scope);
            
            if(!result.isNormal()){
               return result;
            }
            statements[i] = null;
            last = result;
         }
      }
      return last;
   }
}