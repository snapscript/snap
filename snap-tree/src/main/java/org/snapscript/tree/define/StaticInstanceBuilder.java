package org.snapscript.tree.define;

import static org.snapscript.core.StateType.OBJECT;

import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Stack;
import org.snapscript.core.Type;
import org.snapscript.core.define.Instance;
import org.snapscript.core.define.StaticInstance;

public class StaticInstanceBuilder {
   
   private final Type type;
   
   public StaticInstanceBuilder(Type type) {
      this.type = type;
   }

   public Instance create(Scope scope, Instance base, Type real) throws Exception {
      if(base == null) {
         Module module = type.getModule();
         Model model = scope.getModel();
         Scope inner = real.getScope();
         Stack stack = inner.getStack();
         int mask = type.getOrder();
         
         return new StaticInstance(stack, module, model, inner, real, mask | OBJECT.mask);
      }
      return base;
   }
}
