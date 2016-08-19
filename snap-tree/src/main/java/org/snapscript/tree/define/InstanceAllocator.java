package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_THIS;

import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.define.Instance;
import org.snapscript.core.define.ObjectInstance;
import org.snapscript.core.function.Invocation;

public class InstanceAllocator implements Allocator {
   
   private final Initializer initializer;
   private final Invocation invocation;
   
   public InstanceAllocator(Initializer initializer, Invocation invocation) {
      this.initializer = initializer;
      this.invocation = invocation;
   }
   
   @Override
   public Instance allocate(Scope scope, Instance instance, Object... list) throws Exception {
      Type real = (Type)list[0];
      Model model = scope.getModel();
      Module module = real.getModule();
      Class type = instance.getClass();
      
      if(type != ObjectInstance.class) { 
         Instance object = new ObjectInstance(module, model, instance, real);// we need to pass the base type up!!
   
         State state = object.getState();
         Value constant = ValueType.getReference(object, real);
    
         state.addConstant(TYPE_THIS, constant); // reference to 'this'
         initializer.execute(object, real);
         invocation.invoke(object, object, list);
         
         return object;    
      }
      invocation.invoke(instance, instance, list);
      
      return instance;
   }
}
