
package org.snapscript.tree.variable;

import java.lang.reflect.Proxy;
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
   private final VariableBinder binder;
   
   public VariablePointerResolver(VariableBinder binder) {
      this.cache = new CopyOnWriteCache<Object, VariablePointer>();
      this.builder = new VariableKeyBuilder();
      this.binder = binder;
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
            return new ModulePointer(name);
         }
         if(Map.class.isInstance(left)) {
            return new MapPointer(name);
         }         
         if(Scope.class.isInstance(left)) {
            return new ScopePointer(name);
         }
         if(Type.class.isInstance(left)) {
            return new TypePointer(name);
         }
         if(Collection.class.isInstance(left)) {
            return new CollectionPointer(name);
         }
         if(Proxy.class.isInstance(left)) {
            return new ProxyPointer(binder, name);
         }
         if(type.isArray()) {
            return new ArrayPointer(name);
         }
         return new ObjectPointer(name);
      }
      if(Instance.class.isInstance(scope)) {
         return new InstancePointer(name);
      }
      return new LocalPointer(name);
   }
}
