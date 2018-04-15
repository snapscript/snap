package org.snapscript.tree.define;

import org.snapscript.core.Statement;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeState;

public class InnerTypeCompiler extends TypeState {
   
   private final Statement statement;
   
   public InnerTypeCompiler(Statement statement) {
      this.statement = statement;
   }

   @Override
   public void compile(Scope scope, Type type) throws Exception {
      statement.compile(scope,  null);
   }
}
