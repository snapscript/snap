package org.snapscript.tree.define;

import org.snapscript.core.Statement;
import org.snapscript.core.module.Module;

public class ModuleStatement implements ModulePart {
   
   private final Statement statement;
   
   public ModuleStatement(Statement statement) {
      this.statement = statement;
   }

   @Override
   public Statement define(ModuleBody body, Module module) throws Exception {
      return statement;
   }

}
