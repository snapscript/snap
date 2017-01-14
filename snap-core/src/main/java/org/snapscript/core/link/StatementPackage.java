package org.snapscript.core.link;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class StatementPackage implements Package {
   
   private final PackageDefinition definition;
   private final AtomicBoolean define;
   private final Statement statement;
   private final String name;
   private final Path path;
   
   public StatementPackage(Statement statement, Path path, String name) {
      this.definition = new StatementDefinition(statement, path, name);
      this.define = new AtomicBoolean(true);
      this.statement = statement;
      this.name = name;
      this.path = path;
   }

   @Override
   public PackageDefinition define(Scope scope) throws Exception {
      if(define.compareAndSet(true, false)) { // define only once
         Module module = scope.getModule();
         Context context = module.getContext();
         
         try {
            ModuleRegistry registry = context.getRegistry();
            Module library = registry.addModule(name, path); //  we should include path
            Scope inner = library.getScope();
           
            statement.define(inner);
         } catch(Exception e) {
            if(path != null) {
               throw new InternalStateException("Error occured in '" + path + "'", e);
            }
            throw new InternalStateException("Error occured in script", e);
         }
      }
      return definition;
   }

}
