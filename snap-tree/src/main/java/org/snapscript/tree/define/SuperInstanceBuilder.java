package org.snapscript.tree.define;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.scope.instance.SuperInstance;
import org.snapscript.core.type.Type;

public class SuperInstanceBuilder {
   
   private final Type type;
   
   public SuperInstanceBuilder(Type type) {
      this.type = type;
   }

   public Scope create(Scope scope, Object left) throws Exception {
      Type real = (Type)left;
      Instance instance = (Instance)scope;
      Instance outer = instance.getScope();
      Module module = type.getModule();

      return new SuperInstance(module, outer, real, type);
   }
}