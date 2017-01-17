package org.snapscript.core.link;

import org.snapscript.core.Context;
import org.snapscript.core.FilePathConverter;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Path;
import org.snapscript.core.PathConverter;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.TypeLoader;

public class ImportTaskResolver {

   private final PathConverter converter;
   private final Context context;
   private final Path from;
   
   public ImportTaskResolver(Context context, Path from) {
      this.converter = new FilePathConverter();
      this.context = context;
      this.from = from;
   }
   
   public Runnable importTask(Scope scope, String name) throws Exception{
      try {
         TypeLoader loader = context.getLoader();
         Package module = loader.importType(name);
         Path path = converter.createPath(name);
         
         return new Executable(module, scope, path); // compile exceptions will propagate
      } catch(Exception e) {
         return null;
      }
   }
   
   private class Executable implements Runnable {
      
      private final Package module;
      private final Scope scope;
      private final Path path;
      
      public Executable(Package module, Scope scope, Path path){
         this.module = module;
         this.scope = scope;
         this.path = path;
      }

      @Override
      public void run() {
         try {
            PackageDefinition definition = module.define(scope);
            Statement statement = definition.compile(scope, from);
            
            statement.execute(scope); 
         } catch(Exception e) {
            throw new InternalStateException("Could not compile '" + path+"'", e);
         }
      }
      
   }
}
