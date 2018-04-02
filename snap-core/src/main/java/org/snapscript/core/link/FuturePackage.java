package org.snapscript.core.link;

import java.util.concurrent.FutureTask;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;

public class FuturePackage implements Package {
   
   private final FutureTask<Package> result;
   private final Path path;
   
   public FuturePackage(FutureTask<Package> result, Path path) {
      this.result = result;
      this.path = path;
   }
   
   @Override
   public PackageDefinition create(Scope scope) throws Exception {
      Package library = result.get();
      
      if(library == null) {
         throw new InternalStateException("Could not define '" + path + "'");
      }
      return library.create(scope);
   }      
}