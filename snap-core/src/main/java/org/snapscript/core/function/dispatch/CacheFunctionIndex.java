package org.snapscript.core.function.dispatch;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.search.FunctionSearcher;

public class CacheFunctionIndex implements FunctionGroup {
   
   private final Cache<Class, FunctionDispatcher> cache;
   private final FunctionDispatcherBuilder builder;
   private final FunctionDispatcher instance;
   private final FunctionDispatcher local;
   private final FunctionDispatcher empty;
   
   public CacheFunctionIndex(FunctionSearcher binder, ErrorHandler handler, String name) {
      this.builder = new FunctionDispatcherBuilder(binder, handler, name);
      this.cache = new CopyOnWriteCache<Class, FunctionDispatcher>();
      this.instance = new InstanceDispatcher(binder, handler, name);
      this.local = new LocalDispatcher(binder, handler, name);
      this.empty = new EmptyDispatcher();
   }
   
   public FunctionDispatcher get(Scope scope) throws Exception {
      Type type = scope.getType();
      
      if(type != null) {
         return instance;
      }
      return local;
   }
   
   public FunctionDispatcher get(Scope scope, Constraint left) throws Exception {
      Type type = left.getType(scope);
      
      if(type != null) {
         return builder.create(scope, left);
      }
      return empty;
   }
   
   public FunctionDispatcher get(Scope scope, Object left) throws Exception {
      Type type = scope.getType();
      
      if(left != null) {
         Class key = left.getClass();
         FunctionDispatcher dispatcher = cache.fetch(key); // key seems wrong?
         
         if(dispatcher == null) { 
            dispatcher = builder.create(scope, key);
            cache.cache(key, dispatcher);
         }
         return dispatcher;
      }
      if(type != null) {
         return instance;
      }
      return local;
   }

}