package org.snapscript.core.define;

import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;

public class StaticInstance extends PrimitiveInstance {

   public StaticInstance(State stack, Module module, Model model, Scope scope, Type type) {
      super(stack, module, model, scope, type);
   }

}
