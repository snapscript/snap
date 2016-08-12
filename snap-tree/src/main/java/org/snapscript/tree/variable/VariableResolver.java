package org.snapscript.tree.variable;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueKeyBuilder;

public class VariableResolver {
   
   private final Cache<Object, ValueResolver> resolvers;
   private final ValueKeyBuilder builder;
   private final VariableBinder binder;
   
   public VariableResolver() {
      this.resolvers = new CopyOnWriteCache<Object, ValueResolver>();
      this.builder = new ValueKeyBuilder();
      this.binder = new VariableBinder();
   }
   
   public Value resolve(Scope scope, Object left, String name) throws Exception {
      Object key = builder.create(scope, left, name);
      ValueResolver resolver = resolvers.fetch(key);
      
      if(resolver == null) { 
         resolver = binder.bind(scope, left, name);
         resolvers.cache(key, resolver);
      }
      return resolver.resolve(scope, left);
   }
}
