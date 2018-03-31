package org.snapscript.core.function.dispatch;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.find.FunctionFinder;

public class CacheFunctionIndexer implements FunctionBinder {

   private final Cache<String, FunctionGroup> cache;
   private final FunctionFinder binder;
   private final ErrorHandler handler;
   
   public CacheFunctionIndexer(FunctionFinder binder, ErrorHandler handler) {
      this.cache = new CopyOnWriteCache<String, FunctionGroup>();
      this.handler = handler;
      this.binder = binder;
   }
   
   @Override
   public FunctionGroup bind(String name){
      FunctionGroup index = cache.fetch(name);
      
      if(index == null) {
         index = new CacheFunctionIndex(binder, handler, name);
         cache.cache(name, index);
      }
      return index;
   }
   
}