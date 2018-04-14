package org.snapscript.compile.assemble;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Context;
import org.snapscript.core.Execution;
import org.snapscript.core.ApplicationValidator;
import org.snapscript.core.Statement;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageDefinition;
import org.snapscript.core.scope.Scope;

public class ApplicationCompiler {
   
   private final AtomicReference<Execution> cache;
   private final Package library;
   private final Context context;
   
   public ApplicationCompiler(Context context, Package library){
      this.cache = new AtomicReference<Execution>();
      this.library = library;
      this.context = context;
   }

   public Execution compile(Scope scope) throws Exception{ 
      Execution execution = cache.get();
      
      if(execution == null) {
         ApplicationValidator validator = context.getValidator();
         PackageDefinition definition = library.create(scope); // create all types
         Statement statement = definition.define(scope, null); // define tree
         Execution result = statement.compile(scope, null);
         
         validator.validate(context); // validate program
         cache.set(result);
         
         return result;
      }
      return execution;
   }
}