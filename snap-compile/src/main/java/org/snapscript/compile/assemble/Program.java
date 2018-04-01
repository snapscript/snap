package org.snapscript.compile.assemble;

import org.snapscript.compile.Executable;
import org.snapscript.core.Context;
import org.snapscript.core.EmptyModel;
import org.snapscript.core.Execution;
import org.snapscript.core.Model;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.ScopeMerger;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.link.Package;

public class Program implements Executable{
   
   private final ProgramCompiler compiler;
   private final ScopeMerger merger;
   private final Context context;
   private final Model model;
   private final String name;
   private final Path path;
   
   public Program(Context context, Package library, Path path, String name){
      this.compiler = new ProgramCompiler(context, library);
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
      Scope scope = merger.merge(model, name, path);
      ErrorHandler handler = context.getHandler();
      
      try {
         Execution execution = compiler.compile(scope); // create all types
         execution.execute(scope);
      } catch(Throwable cause) {
         handler.handleExternalError(scope, cause);
      }
   }
}