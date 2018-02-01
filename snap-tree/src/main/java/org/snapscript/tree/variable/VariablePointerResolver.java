package org.snapscript.tree.variable;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Scope;
import org.snapscript.tree.NameReference;

public class VariablePointerResolver {
   
   private final Cache<Integer, VariablePointer> cache;
   private final VariablePointerBuilder builder;
   private final VariableIndexResolver resolver;
   
   public VariablePointerResolver(NameReference reference) {
      this.cache = new CopyOnWriteCache<Integer, VariablePointer>();
      this.builder = new VariablePointerBuilder(reference);
      this.resolver = new VariableIndexResolver();
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