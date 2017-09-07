package org.snapscript.tree.define;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
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
      Instance outer = instance.getScope();
      Module module = type.getModule();

      return new SuperInstance(module, outer, real, type);
   }
}