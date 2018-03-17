package org.snapscript.core.link;

import org.snapscript.core.Scope;

public class NoPackage implements Package {

   @Override
   public PackageDefinition create(Scope scope) throws Exception {
      return new NoDefinition();
   }
}