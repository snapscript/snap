package org.snapscript.core.stack;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class CompoundStatement2  {
   
   private final Statement[] statements;
   private final AtomicBoolean compile;
   private final AtomicInteger depth;
   
   public CompoundStatement2(Statement... statements) {
      this.compile = new AtomicBoolean();
      this.depth = new AtomicInteger();
      this.statements = statements;
   }
   
   public Result compile(Scope scope) throws Exception {
      Result last = ResultType.getNormal();
      
      if(compile.get()) {
         throw new InternalStateException("Statement was already compiled");
      }
      try {
         for(Statement statement : statements) {
            Result result = statement.compile(scope);
            
            if(result.isDeclare()){
               depth.getAndIncrement();
            }
         }
      } finally {
         compile.set(true);
      }
      return last;
   }
   
   public Result execute(Scope2 scope) throws Exception {
      Result last = ResultType.getNormal();
      State2 state2 = scope.getStack();
      State2 next = state2.create(); // allow the stack to grow in a compound statement!!
      
      if(!compile.get()) {
         throw new InternalStateException("Statement was not compiled");
      }
      try {
         for(Statement statement : statements) {
            Result result = statement.execute(null); // execute(scope); NOT NEED FOR CHANGE
            
            if(!result.isNormal()){
               return result;
            }
            last = result;
         }
      }finally {
         next.clear();
      }
      return last;
   }
   
   
}
