package org.snapscript.core.link;

import java.util.concurrent.FutureTask;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class FutureDefinition implements PackageDefinition {
   
   private final FutureTask<PackageDefinition> result;
   private final Path path;
   
   public FutureDefinition(FutureTask<PackageDefinition> result, Path path) {
      this.result = result;
      this.path = path;
   }

   @Override
   public Statement compile(Scope scope, Path from) throws Exception {
      PackageDefinition definition = result.get();
      
      if(definition == null) {
         throw new InternalStateException("Could not compile '" + path + "'");
      }
      return definition.compile(scope, from);
   }     
}
