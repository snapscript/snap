package org.snapscript.tree.variable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueKeyBuilder;

public class VariableResolver {
   
   private final Map<Object, ValueResolver> resolvers;
   private final ValueKeyBuilder builder;
   private final VariableBinder binder;
   
   public VariableResolver() {
      this.resolvers = new ConcurrentHashMap<Object, ValueResolver>();
      this.builder = new ValueKeyBuilder();
      this.binder = new VariableBinder();
   }
   
   public Value resolve(Scope scope, Object left, String name) throws Exception {
      Object key = builder.create(scope, left, name);
      ValueResolver resolver = resolvers.get(key);
      
      if(resolver == null) { 
         resolver = binder.bind(scope, left, name);
         resolvers.put(key, resolver);
      }
      return resolver.resolve(scope, left);
   }
}
