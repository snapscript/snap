package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_THIS;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.type.Type;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.variable.Value;

public class ThisAllocator implements TypeAllocator {
   
   private final ObjectInstanceBuilder builder;
   private final Invocation invocation;
   private final TypeBody body;
   
   public ThisAllocator(TypeBody body, Invocation invocation, Type type) {
      this.builder = new ObjectInstanceBuilder(type);
      this.invocation = invocation;
      this.body = body;
   }
   
   @Override
   public Instance allocate(Scope scope, Instance base, Object... list) throws Exception {
      Type real = (Type)list[0];
      Instance instance = builder.create(scope, base, real); // we need to pass the base type up!
      State state = instance.getState();
      Value value = state.get(TYPE_THIS);
      
      if(instance != base) { // false if this(...) is called
         value.setValue(instance); // set the 'this' variable
         body.execute(instance, real);
         invocation.invoke(instance, instance, list);
         
         return instance;
      }
      invocation.invoke(instance, instance, list);
      
      return instance;    
   }
}