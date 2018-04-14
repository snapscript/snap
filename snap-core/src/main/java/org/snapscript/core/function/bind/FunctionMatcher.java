package org.snapscript.core.function.bind;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.dispatch.EmptyDispatcher;
import org.snapscript.core.function.dispatch.FunctionDispatcher;
import org.snapscript.core.function.dispatch.FunctionDispatcherBuilder;
import org.snapscript.core.function.dispatch.LocalDispatcher;
import org.snapscript.core.function.dispatch.TypeLocalDispatcher;
import org.snapscript.core.function.resolve.FunctionResolver;

public class FunctionMatcher {
   
   private final Cache<Class, FunctionDispatcher> cache;
   private final FunctionDispatcherBuilder builder;
   private final FunctionDispatcher instance;
   private final FunctionDispatcher local;
   private final FunctionDispatcher empty;
   
   public FunctionMatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.builder = new FunctionDispatcherBuilder(resolver, handler, name);
      this.cache = new CopyOnWriteCache<Class, FunctionDispatcher>();
      this.instance = new TypeLocalDispatcher(resolver, handler, name);
      this.local = new LocalDispatcher(resolver, handler, name);
      this.empty = new EmptyDispatcher();
   }
   
   public FunctionDispatcher match(Scope scope) throws Exception {
      Type type = scope.getType();
      
      if(type != null) {
         return instance;
      }
      return local;
   }
   
   public FunctionDispatcher match(Scope scope, Constraint left) throws Exception {
      Type type = left.getType(scope);
      
      if(type != null) {
         return builder.create(scope, left);
      }
      return empty;
   }
   
   public FunctionDispatcher match(Scope scope, Object left) throws Exception {
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