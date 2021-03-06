package org.snapscript.compile;

import org.snapscript.common.store.ClassPathStore;
import org.snapscript.common.store.Store;
import org.snapscript.core.Context;

public class ClassPathCompilerBuilder {   
   
   public static Compiler createCompiler() {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      return new StringCompiler(context);
   }
}
