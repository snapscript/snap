package org.snapscript.tree;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class CompoundStatement extends Statement {
   
   private final Statement[] statements;
   private final AtomicBoolean compile;
   private final AtomicInteger depth;
   
   public CompoundStatement(Statement... statements) {
      this.compile = new AtomicBoolean();
      this.depth = new AtomicInteger();
      this.statements = statements;
   }
   
   @Override
   public Result compile(Scope scope) throws Exception {
      Result last = ResultType.getNormal();
      
      if(compile.compareAndSet(false, true)) {
         for(Statement statement : statements) {
            Result result = statement.compile(scope);
            
            if(result.isDeclare()){
               depth.getAndIncrement();
            }
         }
      }
      return last;
   }
   
   @Override
   public Result execute(Scope scope) throws Exception {
      Result last = ResultType.getNormal();
      
      if(!compile.get()) {
         throw new InternalStateException("Statement was not compiled");
      }
      Scope compound = create(scope);
      
      for(Statement statement : statements) {
         Result result = statement.execute(compound);
         
         if(!result.isNormal()){
            return result;
         }
         last = result;
      }
      return last;
   }
   
   private Scope create(Scope scope) throws Exception {
      int count = depth.get();
      
      if(count > 0) {
         return scope.getInner();
      }
      return scope;
   }
   
}
