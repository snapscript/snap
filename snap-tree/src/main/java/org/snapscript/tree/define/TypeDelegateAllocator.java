package org.snapscript.tree.define;

import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.instance.Instance;

public class TypeDelegateAllocator implements TypeAllocator {
   
   private final InvocationBuilder builder;
   private final TypeAllocator allocator;
   
   public TypeDelegateAllocator(TypeAllocator allocator, InvocationBuilder builder) {
      this.allocator = allocator;
      this.builder = builder;
   }

   @Override
   public Instance allocate(Scope scope, Instance object, Object... list) throws Exception {
      Invocation invocation = builder.create(object);
      Instance base = (Instance)invocation.invoke(scope, object, list);
      
      return allocator.allocate(scope, base, list);
   }
}