package org.snapscript.tree.define;

import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Stack;
import org.snapscript.core.Type;
import org.snapscript.core.define.Instance;
import org.snapscript.core.define.SuperInstance;

public class SuperInstanceBuilder {
   
   private final Type type;
   
   public SuperInstanceBuilder(Type type) {
      this.type = type;
   }

   public Scope create(Scope scope, Object left) throws Exception {
      Type real = (Type)left;
      Instance instance = (Instance)scope;
      Instance outer = instance.getObject();
      Module module = type.getModule();
      Model model = scope.getModel();
      Stack stack = instance.getStack();

      return new SuperInstance(stack, module, model, outer, real, type);
   }
}
