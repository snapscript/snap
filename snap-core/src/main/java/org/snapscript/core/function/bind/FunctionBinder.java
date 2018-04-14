package org.snapscript.core.function.bind;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.search.FunctionSearcher;

public class FunctionBinder {

   private final Cache<String, FunctionMatcher> cache;
   private final FunctionSearcher binder;
   private final ErrorHandler handler;
   
   public FunctionBinder(FunctionSearcher binder, ErrorHandler handler) {
      this.cache = new CopyOnWriteCache<String, FunctionMatcher>();
      this.handler = handler;
      this.binder = binder;
   }
   
   public FunctionMatcher bind(String name){
      FunctionMatcher index = cache.fetch(name);
      
      if(index == null) {
         index = new FunctionMatcher(binder, handler, name);
         cache.cache(name, index);
      }
      return index;
   }
   
}