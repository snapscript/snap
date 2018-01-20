package org.snapscript.tree.define;

import org.snapscript.core.Statement;

public class ModuleStatement implements ModulePart {
   
   private final Statement statement;
   
   public ModuleStatement(Statement statement) {
      this.statement = statement;
   }

   @Override
   public Statement define(ModuleBody body) throws Exception {
      return statement;
   }

}
