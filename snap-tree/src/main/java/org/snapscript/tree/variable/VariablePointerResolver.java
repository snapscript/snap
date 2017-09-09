package org.snapscript.tree.variable;

import java.util.Collection;
import java.util.Map;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Instance;

public class VariablePointerResolver {
   
   private final Cache<Object, VariablePointer> cache;
   private final VariableKeyBuilder builder;
   private final ConstantResolver resolver;
   
   public VariablePointerResolver() {
      this.cache = new CopyOnWriteCache<Object, VariablePointer>();
      this.builder = new VariableKeyBuilder();
      this.resolver = new ConstantResolver();
   }
   
   public VariablePointer resolve(Scope scope, Object left, String name) {
      Object key = builder.create(scope, left, name);
      VariablePointer pointer = cache.fetch(key);
      
      if(pointer == null) { 
         pointer = create(scope, left, name);
         cache.cache(key, pointer);
      }
      return pointer;
   }
   
   private VariablePointer create(Scope scope, Object left, String name) {
      if(left != null) {
         Class type = left.getClass();
         
         if(Module.class.isInstance(left)) {
            return new ModulePointer(resolver, name);
         }
         if(Map.class.isInstance(left)) {
            return new MapPointer(resolver, name);
         }         
         if(Scope.class.isInstance(left)) {
            return new ScopePointer(name);
         }
         if(Type.class.isInstance(left)) {
            return new TypePointer(resolver, name);
         }
         if(Collection.class.isInstance(left)) {
            return new CollectionPointer(resolver, name);
         }
         if(type.isArray()) {
            return new ArrayPointer(resolver, name);
         }
         return new ObjectPointer(resolver, name);
      }
      if(Instance.class.isInstance(scope)) {
         return new InstancePointer(resolver, name);
      }
      return new LocalPointer(resolver, name);
   }
}