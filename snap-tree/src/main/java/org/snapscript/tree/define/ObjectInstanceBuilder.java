package org.snapscript.tree.define;

import static org.snapscript.core.StateType.OBJECT;

import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Stack;
import org.snapscript.core.Type;
import org.snapscript.core.define.Instance;
import org.snapscript.core.define.ObjectInstance;

public class ObjectInstanceBuilder {
 
   private final Type type;
   
   public ObjectInstanceBuilder(Type type) {
      this.type = type;
   }

   public Instance create(Scope scope, Instance base, Type real) throws Exception {
      Class actual = base.getClass();
      
      if(actual != ObjectInstance.class) { // false if this(...) is called
         Model model = scope.getModel();
         Module module = type.getModule();
         Stack state = scope.getStack();
         int mask = type.getOrder();
         
         return new ObjectInstance(state, module, model, base, real, mask | OBJECT.mask); 
      }
      return base;
   }
}
