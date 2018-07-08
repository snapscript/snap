package org.snapscript.tree;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Statement;
import org.snapscript.core.link.ExceptionStatement;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;

public class Import implements Compilation {

   private final ImportBuilder builder;  
   
   public Import(Qualifier qualifier) {
      this(qualifier, null);
   }
   
   public Import(Qualifier qualifier, Evaluation alias) {
      this.builder = new ImportBuilder(qualifier, alias);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getImport(module, path, line);
      
      try {
         return builder.create(module, path, line);
      } catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
         return new ExceptionStatement("Could not process import", cause);
      }
   }
}