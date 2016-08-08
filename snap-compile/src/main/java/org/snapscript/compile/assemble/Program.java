package org.snapscript.compile.assemble;

import org.snapscript.compile.Executable;
import org.snapscript.core.Context;
import org.snapscript.core.ProgramValidator;
import org.snapscript.core.EmptyModel;
import org.snapscript.core.Model;
import org.snapscript.core.Scope;
import org.snapscript.core.ScopeMerger;
import org.snapscript.core.Statement;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.link.Package;

public class Program implements Executable{
   
   private final ScopeMerger merger;
   private final Package library;
   private final Context context;
   private final Model model;
   private final String name;
   private final String path;
   
   public Program(Context context, Package library, String name, String path){
      this.merger = new ScopeMerger(context);
      this.model = new EmptyModel();
      this.library = library;
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
      Statement script = library.compile(scope); // define method should be invoked first
      ErrorHandler handler = context.getHandler();
      ProgramValidator validator = context.getValidator();
      
      try {
         validator.validate(context);
         script.execute(scope);
      } catch(Throwable cause) {
         handler.throwExternal(scope, cause);
      }
   }
}
