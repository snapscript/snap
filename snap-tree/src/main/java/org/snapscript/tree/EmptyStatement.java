package org.snapscript.tree;

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.parse.StringToken;

public class EmptyStatement extends Statement {
   
   private final StringToken token;
   
   public EmptyStatement() {
      this(null);
   }
   
   public EmptyStatement(StringToken token) {
      this.token = token;
   }

   @Override
   public Result execute(Scope scope) throws Exception {
      return ResultType.getNormal();
   }

}
