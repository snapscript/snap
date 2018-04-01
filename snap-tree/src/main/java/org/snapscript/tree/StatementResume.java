package org.snapscript.tree;

import org.snapscript.core.Execution;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.yield.Resume;

public class StatementResume extends Execution implements Resume {
   
   private final Execution statement;
   
   public StatementResume(Execution statement) {
      this.statement = statement;
   }
   
   @Override
   public Result execute(Scope scope) throws Exception {
      return statement.execute(scope);
   }
   
   @Override
   public Result resume(Scope scope, Object value) throws Exception {
      return statement.execute(scope);
   }

   @Override
   public Resume suspend(Result result, Resume resume, Object value) throws Exception {
      return null;
   }
   
}
