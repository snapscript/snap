package org.snapscript.core.dispatch;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;

public class CallBinder {
   
   private final Cache<Class, CallDispatcher> cache;
   private final CallDispatcherBuilder builder;
   private final CallDispatcher instance;
   private final CallDispatcher local;
   private final CallDispatcher empty;
   
   public CallBinder(FunctionBinder binder, ErrorHandler handler, String name) {
      this.builder = new CallDispatcherBuilder(binder, handler, name);
      this.cache = new CopyOnWriteCache<Class, CallDispatcher>();
      this.instance = new InstanceDispatcher(binder, handler, name);
      this.local = new LocalDispatcher(binder, handler, name);
      this.empty = new EmptyDispatcher();
   }
   
   public CallDispatcher bind(Scope scope) throws Exception {
      Type type = scope.getType();
      
      if(type != null) {
         return instance;
      }
      return local;
   }
   
   public CallDispatcher bind(Scope scope, Constraint left) throws Exception {
      Type type = left.getType(scope);
      
      if(type != null) {
         return builder.create(scope, left);
      }
      return empty;
   }
   
   public CallDispatcher bind(Scope scope, Object left) throws Exception {
      Type type = scope.getType();
      
      if(left != null) {
         Class key = left.getClass();
         CallDispatcher dispatcher = cache.fetch(key);
         
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