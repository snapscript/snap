package org.snapscript.tree.define;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.define.Instance;
import org.snapscript.core.function.Invocation;

public class NewInvocation implements Invocation<Instance>{
   
   private final StaticInstanceBuilder builder;
   private final TypeAllocator allocator;
   private final AtomicBoolean compile;
   private final TypeFactory factory;
   private final Type type;
   
   public NewInvocation(TypeFactory factory, TypeAllocator allocator, Type type) {
      this(factory, allocator, type, true);
   }
   
   public NewInvocation(TypeFactory factory, TypeAllocator allocator, Type type, boolean compile) {
      this.builder = new StaticInstanceBuilder(type);
      this.compile = new AtomicBoolean(compile);
      this.allocator = allocator;
      this.factory = factory;
      this.type = type;
   }

   @Override
   public Result invoke(Scope scope, Instance base, Object... list) throws Exception {
      Type real = (Type)list[0];
      Instance inner = builder.create(scope, base, real);

      if(compile.compareAndSet(true, false)) {
         factory.compile(scope, type); // static stuff if needed
      }
      Instance result = allocator.allocate(scope, inner, list);
      return ResultType.getNormal(result);
   }
}