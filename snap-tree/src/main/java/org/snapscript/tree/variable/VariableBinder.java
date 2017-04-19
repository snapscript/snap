
package org.snapscript.tree.variable;

import java.util.Collection;
import java.util.Map;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.ValueKeyBuilder;
import org.snapscript.core.define.Instance;

public class VariableBinder {
   
   private final Cache<Object, ValueResolver> resolvers;
   private final ValueKeyBuilder builder;
   
   public VariableBinder() {
      this.resolvers = new CopyOnWriteCache<Object, ValueResolver>();
      this.builder = new ValueKeyBuilder();
   }
   
   public ValueResolver bind(Scope scope, Object left, String name) {
      Object key = builder.create(scope, left, name);
      ValueResolver resolver = resolvers.fetch(key);
      
      if(resolver == null) { 
         resolver = create(scope, left, name);
         resolvers.cache(key, resolver);
      }
      return resolver;
   }
   
   private ValueResolver create(Scope scope, Object left, String name) {
      if(left != null) {
         Class type = left.getClass();
         
         if(Module.class.isInstance(left)) {
            return new ModuleResolver(name);
         }
         if(Map.class.isInstance(left)) {
            return new MapResolver(name);
         }         
         if(Scope.class.isInstance(left)) {
            return new ScopeResolver(name);
         }
         if(Type.class.isInstance(left)) {
            return new TypeResolver(name);
         }
         if(Collection.class.isInstance(left)) {
            return new CollectionResolver(name);
         }
         if(type.isArray()) {
            return new ArrayResolver(name);
         }
         return new ObjectResolver(name);
      }
      if(Instance.class.isInstance(scope)) {
         return new InstanceResolver(name);
      }
      return new LocalResolver(name);
   }
}
