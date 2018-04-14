package org.snapscript.core.variable.index;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class VariableIndexer {
   
   private final AtomicReference<VariablePointer> reference;
   private final Cache<Integer, VariablePointer> cache;
   private final VariablePointerBuilder builder;
   private final VariableIndexResolver resolver;
   private final VariablePointer empty;
   
   public VariableIndexer(String name) {
      this.cache = new CopyOnWriteCache<Integer, VariablePointer>();
      this.reference = new AtomicReference<VariablePointer>();
      this.builder = new VariablePointerBuilder(name);
      this.resolver = new VariableIndexResolver();
      this.empty = new EmptyPointer();
   }
   
   public VariablePointer index(Scope scope) throws Exception {
      VariablePointer pointer = reference.get();

      if(pointer == null) { 
         pointer = builder.create(scope);
         reference.set(pointer);
      }
      return pointer;
   }

   public VariablePointer index(Scope scope, Object left) throws Exception {
      int index = resolver.resolve(scope, left);
      VariablePointer pointer = cache.fetch(index);

      if(pointer == null) { 
         pointer = builder.create(scope, left);
         cache.cache(index, pointer);
      }
      return pointer;
   }
   
   public VariablePointer index(Scope scope, Constraint left) throws Exception {
      Type type = left.getType(scope);
      
      if(type != null) {
         return builder.create(scope, left);
      }
      return empty;
   }
}