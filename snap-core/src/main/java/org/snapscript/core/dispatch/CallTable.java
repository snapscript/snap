package org.snapscript.core.dispatch;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.error.ErrorHandler;

public class CallTable {

   private final Cache<String, CallBinder> cache;
   private final FunctionBinder binder;
   private final ErrorHandler handler;
   
   public CallTable(FunctionBinder binder, ErrorHandler handler) {
      this.cache = new CopyOnWriteCache<String, CallBinder>();
      this.handler = handler;
      this.binder = binder;
   }
   
   public CallBinder resolve(String name){
      CallBinder match = cache.fetch(name);
      
      if(match == null) {
         match = new CallBinder(binder, handler, name);
         cache.cache(name, match);
      }
      return match;
   }
   
}
