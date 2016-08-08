package org.snapscript.compile;

import org.snapscript.compile.Compiler;
import org.snapscript.compile.StoreContext;
import org.snapscript.compile.StringCompiler;
import org.snapscript.core.Context;
import org.snapscript.core.store.ClassPathStore;
import org.snapscript.core.store.Store;

public class ClassPathCompilerBuilder {
   
   //private static final Executor EXECUTOR = Executors.newFixedThreadPool(3);

   public static Compiler createCompiler() {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      
      return new StringCompiler(context);
   }
}
