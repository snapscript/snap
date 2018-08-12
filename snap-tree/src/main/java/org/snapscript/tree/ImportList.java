package org.snapscript.tree;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Statement;
import org.snapscript.core.link.ExceptionStatement;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;

public class ImportList implements Compilation {

   private final Statement[] statements;
   private final Qualifier qualifier;
   private final Qualifier[] names;

   public ImportList(Qualifier qualifier, Qualifier... names) {
      this.statements = new Statement[names.length];
      this.qualifier = qualifier;
      this.names = names;
   }

   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getImport(module, path, line);
      String prefix = qualifier.getQualifier();

      for(int i = 0; i < names.length; i++) {
         EntryQualifier entry = new EntryQualifier(prefix, names[i]);
         ImportBuilder builder = new ImportBuilder(entry, null);

         try {
            statements[i] = builder.create(module, path, line);
         } catch (Exception cause) {
            interceptor.traceCompileError(scope, trace, cause);
            return new ExceptionStatement("Could not process import", cause);
         }
      }
      return new StatementBlock(statements);
   }

   private static class EntryQualifier implements Qualifier {

      private final String target;
      private final String prefix;

      public EntryQualifier(String prefix, Qualifier target) {
         this.target = target.getQualifier();
         this.prefix = prefix;
      }

      @Override
      public String getQualifier() {
         return prefix;
      }

      @Override
      public String getLocation() {
         return prefix;
      }

      @Override
      public String getTarget() {
         return target;
      }

      @Override
      public String getName() {
         return target;
      }
   }
}