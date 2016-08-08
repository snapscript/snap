package org.snapscript.core.bind;

import org.snapscript.core.Module;

public class ModuleCacheIndexer implements FunctionCacheIndexer<Module> {
   
   public ModuleCacheIndexer() {
      super();
   }
   
   public int index(Module module) {
      return module.getOrder();
   }
}
