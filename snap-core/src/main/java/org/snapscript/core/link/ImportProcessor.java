package org.snapscript.core.link;

import org.snapscript.core.Context;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.TypeLoader;

public class ImportProcessor {

   private final Context context;
   private final Path path;
   
   public ImportProcessor(Context context, Path path) {
      this.context = context;
      this.path = path;
   }
   
   public boolean importType(Scope scope, String name) throws Exception{
      try {
         TypeLoader loader = context.getLoader();
         Package result = loader.importType(name);
         PackageDefinition definition = result.define(scope);
         Statement statement = definition.compile(scope, path);
         
         statement.execute(scope);
      } catch(Exception e) {
         return false;
      }
      return true;
   }
}
