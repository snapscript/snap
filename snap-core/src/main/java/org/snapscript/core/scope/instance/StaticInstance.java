package org.snapscript.core.scope.instance;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class StaticInstance extends PrimitiveInstance {

   public StaticInstance(Module module, Scope scope, Type type) {
      super(module, scope, type);
   }

}