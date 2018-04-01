package org.snapscript.core.link;

import org.snapscript.core.NoStatement;
import org.snapscript.core.Statement;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;

public class NoDefinition implements PackageDefinition {

   @Override
   public Statement define(Scope scope, Path from) throws Exception {
      return new NoStatement();
   }

}