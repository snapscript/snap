package org.snapscript.tree.define;

import org.snapscript.core.Bug;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.scope.instance.SuperInstance;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class SuperInstanceBuilder {
   
   private final Type type;
   
   public SuperInstanceBuilder(Type type) {
      this.type = type;
   }

   @Bug
   public Scope create(Scope scope, Value left) throws Exception {
      Type real = left.getValue();
      Instance instance = (Instance)scope;
      Instance outer = instance.getScope();
      Module module = type.getModule();

      return new SuperInstance(module, outer, real, type);
   }
}
