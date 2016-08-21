package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_THIS;

import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.define.Instance;
import org.snapscript.core.function.Invocation;

public class InstanceAllocator implements Allocator {
   
   private final ObjectInstanceBuilder builder;
   private final Initializer initializer;
   private final Invocation invocation;
   
   public InstanceAllocator(Initializer initializer, Invocation invocation, Type type) {
      this.builder = new ObjectInstanceBuilder(type);
      this.initializer = initializer;
      this.invocation = invocation;
   }
   
   @Override
   public Instance allocate(Scope scope, Instance base, Object... list) throws Exception {
      Type real = (Type)list[0];
      Instance instance = builder.create(scope, base, real);// we need to pass the base type up!!
      State state = instance.getState();
      Value value = state.getValue(TYPE_THIS);
      
      if(instance != base) { // false if this(...) is called
         initializer.execute(instance, real);
      }
      value.setValue(instance); // set the 'this' variable
      invocation.invoke(instance, instance, list);
      
      return instance;    
   }
}
