package org.snapscript.compile.assemble;

import static org.snapscript.core.error.Reason.THROW;

import org.snapscript.compile.Executable;
import org.snapscript.core.Context;
import org.snapscript.core.Execution;
import org.snapscript.core.ScopeMerger;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.link.Package;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.EmptyModel;
import org.snapscript.core.scope.Model;
import org.snapscript.core.scope.Scope;

public class Application implements Executable{
   
   private final ApplicationCompiler compiler;
   private final ScopeMerger merger;
   private final Context context;
   private final Model model;
   private final String name;
   private final Path path;
   
   public Application(Context context, Package library, Path path, String name){
      this.compiler = new ApplicationCompiler(context, library);
      this.merger = new ScopeMerger(context);
      this.model = new EmptyModel();
      this.context = context;
      this.path = path;
      this.name = name;
   }
   
   @Override
   public void execute() throws Exception {
      execute(model);
   }

   @Override
   public void execute(Model model) throws Exception{
      execute(model, false);
   }
   
   @Override
   public void execute(Model model, boolean test) throws Exception{ 
      Scope scope = merger.merge(model, name, path);
      ErrorHandler handler = context.getHandler();
      Execution execution = compiler.compile(scope); // create all types
      
      try {
         if(!test) {
            execution.execute(scope);
         }
      } catch(Throwable cause) {
         handler.handleExternalError(THROW, scope, cause);
      }
   }
}