package org.snapscript.tree;

import org.snapscript.core.Result;
import org.snapscript.core.Resume;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class StatementResume extends Statement implements Resume {
   
   private final Statement statement;
   
   public StatementResume(Statement statement) {
      this.statement = statement;
   }

   @Override
   public Result compile(Scope scope) throws Exception {
      return statement.compile(scope);
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
