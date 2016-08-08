package org.snapscript.tree.define;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.define.Instance;
import org.snapscript.core.function.Invocation;

public class NewInvocation implements Invocation<Instance>{
   
   private final StaticInstanceBuilder builder;
   private final Initializer initializer;
   private final AtomicBoolean compile;
   private final Allocator allocator;
   private final Type type;
   
   public NewInvocation(Initializer initializer, Allocator allocator, Type type) {
      this(initializer, allocator, type, true);
   }
   
   public NewInvocation(Initializer initializer, Allocator allocator, Type type, boolean compile) {
      this.builder = new StaticInstanceBuilder(type);
      this.compile = new AtomicBoolean(compile);
      this.initializer = initializer;
      this.allocator = allocator;
      this.type = type;
   }

   @Override
   public Result invoke(Scope scope, Instance base, Object... list) throws Exception {
      Type real = (Type)list[0];
      Instance instance = builder.create(scope, base, real); // merge with static inner scope
      
      if(initializer != null) {
         if(compile.compareAndSet(true, false)) {
            initializer.compile(scope, type); // static stuff if needed
         }
      }
      if(instance != null) {
         instance.setInstance(instance); // set temporary instance
      }
      return create(scope, instance, list);
   }
   
   private Result create(Scope scope, Instance base, Object... list) throws Exception {
      Instance result = allocator.allocate(scope, base, list);
      
      if(result != null) {
         result.setInstance(result); // set instance
      }
      return ResultType.getNormal(result);
   }
}
