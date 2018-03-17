package org.snapscript.core.link;

import org.snapscript.core.NoStatement;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class NoDefinition implements PackageDefinition {

   @Override
   public Statement define(Scope scope, Path from) throws Exception {
      return new NoStatement();
   }

}