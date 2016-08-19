package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
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
      Instance inner = builder.create(scope, base, real);

      if(compile.compareAndSet(true, false)) {
         initializer.compile(scope, type); // static stuff if needed
      }
      Instance result = allocator.allocate(scope, inner, list);
      
      if(real == type){
         Instance start = result;
         
         while(start != null){
            State state = start.getState();
            Value value = state.getValue(TYPE_THIS);
            
            if(value != null){
               value.setValue(result);
            }
            start = start.getSuper();
         }
      }
      return ResultType.getNormal(result);
   }
}
