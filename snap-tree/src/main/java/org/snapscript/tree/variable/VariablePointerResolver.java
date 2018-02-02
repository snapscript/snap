package org.snapscript.tree.variable;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Scope;

public class VariablePointerResolver {
   
   private final AtomicReference<VariablePointer> reference;
   private final Cache<Integer, VariablePointer> cache;
   private final VariablePointerBuilder builder;
   private final VariableIndexResolver resolver;
   
   public VariablePointerResolver(String name) {
      this.cache = new CopyOnWriteCache<Integer, VariablePointer>();
      this.reference = new AtomicReference<VariablePointer>();
      this.builder = new VariablePointerBuilder(name);
      this.resolver = new VariableIndexResolver();
   }
   
   public VariablePointer resolve(Scope scope) throws Exception {
      VariablePointer pointer = reference.get();

      if(pointer == null) { 
         pointer = builder.create(scope);
         reference.set(pointer);
      }
      return pointer;
   }

   public VariablePointer resolve(Scope scope, Object left) throws Exception {
      int index = resolver.resolve(scope, left);
      VariablePointer pointer = cache.fetch(index);

      if(pointer == null) { 
         pointer = builder.create(scope, left);
         cache.cache(index, pointer);
      }
      return pointer;
   }
}