package org.snapscript.compile;

import org.snapscript.common.store.ClassPathStore;
import org.snapscript.common.store.Store;
import org.snapscript.core.Context;
import org.snapscript.core.Scope;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceListener;

public class ClassPathCompilerBuilder {
   
   //private static final Executor EXECUTOR = Executors.newFixedThreadPool(3);

   public static Compiler createCompiler() {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      context.getInterceptor().register(new TraceListener() {
         @Override
         public void error(Scope scope, Trace trace, Exception cause) {
            String message = cause.getMessage();
            System.err.printf("%s in %s%n", message, trace);
         }
         public void before(Scope scope, Trace trace) {}
         public void after(Scope scope, Trace trace) {}         
      });
      return new StringCompiler(context);
   }
}
