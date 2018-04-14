package org.snapscript.core.function.bind;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.resolve.FunctionResolver;

public class FunctionBinder {

   private final Cache<String, FunctionMatcher> cache;
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   
   public FunctionBinder(FunctionResolver resolver, ErrorHandler handler) {
      this.cache = new CopyOnWriteCache<String, FunctionMatcher>();
      this.handler = handler;
      this.resolver = resolver;
   }
   
   public FunctionMatcher bind(String name){
      FunctionMatcher index = cache.fetch(name);
      
      if(index == null) {
         index = new FunctionMatcher(resolver, handler, name);
         cache.cache(name, index);
      }
      return index;
   }
   
}