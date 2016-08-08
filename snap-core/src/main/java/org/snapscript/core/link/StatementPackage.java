package org.snapscript.core.link;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class StatementPackage implements Package {
   
   private final AtomicBoolean compile;
   private final Statement statement;
   private final String name;
   private final String path;
   
   public StatementPackage(Statement statement, String name, String path) {
      this.compile = new AtomicBoolean(true);
      this.statement = statement;
      this.name = name;
      this.path = path;
   }

   @Override
   public Statement compile(Scope scope) throws Exception {
      if(compile.compareAndSet(true, false)) { // compile only once
         Module module = scope.getModule();
         Context context = module.getContext();
         
         try {
            ModuleRegistry registry = context.getRegistry();
            Module library = registry.addModule(name, path);
            Scope inner = library.getScope();
           
            statement.define(inner);
            statement.compile(inner); // concurrent compilation means this might happen before define is finished!
         } catch(Exception e) {
            if(path != null) {
               throw new InternalStateException("Error occured in '" + path + "'", e);
            }
            throw new InternalStateException("Error occured in script", e);
         }
      }
      return statement;
   }

}
