package org.snapscript.core.function.bind;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.search.FunctionSearcher;

public class FunctionBinder {

   private final Cache<String, FunctionGroup> cache;
   private final FunctionSearcher binder;
   private final ErrorHandler handler;
   
   public FunctionBinder(FunctionSearcher binder, ErrorHandler handler) {
      this.cache = new CopyOnWriteCache<String, FunctionGroup>();
      this.handler = handler;
      this.binder = binder;
   }
   
   public FunctionGroup bind(String name){
      FunctionGroup index = cache.fetch(name);
      
      if(index == null) {
         index = new FunctionGroup(binder, handler, name);
         cache.cache(name, index);
      }
      return index;
   }
   
}