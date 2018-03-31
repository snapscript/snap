package org.snapscript.tree.define;

import static org.snapscript.core.result.Result.NORMAL;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Execution;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.link.FutureExecution;
import org.snapscript.core.result.Result;

public class ModuleBody extends Statement {

   private final AtomicReference<Execution> reference;
   private final Statement[] statements;
   private final ModulePart[] parts;
   private final AtomicBoolean create;
   private final AtomicBoolean define;
   
   public ModuleBody(ModulePart... parts) {
      this.reference = new AtomicReference<Execution>();
      this.statements = new Statement[parts.length];
      this.define = new AtomicBoolean(true);
      this.create = new AtomicBoolean(true);
      this.parts = parts;
   }
   
   @Override
   public void create(Scope scope) throws Exception {
      if(create.compareAndSet(true, false)) {
         for(int i = 0; i < parts.length; i++) {
            statements[i] = parts[i].define(this);
            statements[i].create(scope);
            parts[i] = null;
         }
      }
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      if(define.compareAndSet(true, false)) {
         for(int i = 0; i < statements.length; i++) {
            statements[i].define(scope);
         }
      }
   }
   
   @Override
   public Execution compile(Scope scope) throws Exception {
      Execution result = reference.get();
      
      if(result == null) {
         ModuleTask job = new ModuleTask(reference, scope, statements);
         FutureTask<Execution> task = new FutureTask<Execution>(job);
         FutureExecution execution = new FutureExecution(task, null);
         
         if(reference.compareAndSet(null, execution)) {
            task.run();
            return task.get();
         }
         return reference.get();
      }
      return result;
   }
   
   private static class ModuleTask implements Callable<Execution> {
      
      private final AtomicReference<Execution> reference;
      private final Statement[] statements;
      private final Scope module;
      
      public ModuleTask(AtomicReference<Execution> reference, Scope module, Statement... statements) {
         this.reference = reference;
         this.statements = statements;
         this.module = module;
      }
      
      @Override
      public Execution call() throws Exception {
         Execution[] executions = new Execution[statements.length];
         Execution execution = new ModuleExecution(module, executions);
         
         for(int i = 0; i < statements.length; i++) {
            executions[i] = statements[i].compile(module);
         }
         reference.set(execution);
         return execution;
      }
   }   
 
   
   private static class ModuleExecution extends Execution {
      
      private final Execution[] executions;
      private final AtomicBoolean execute;
      private final Scope module;
      
      public ModuleExecution(Scope module, Execution... executions) {
         this.execute = new AtomicBoolean(true);
         this.executions = executions;
         this.module = module;
      }
  
      @Override
      public Result execute(Scope scope) throws Exception {
         Result last = NORMAL;
         
         if(execute.compareAndSet(true, false)) {  
            Scope s = module.getModule().getScope();
            
            for(int i = 0; i < executions.length; i++) {
               Result result = executions[i].execute(s);
               
               if(!result.isNormal()){
                  return result;
               }
               last = result;
            }
         }
         return last;
      }
   }
}