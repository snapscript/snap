package org.snapscript.core.define;

import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Stack;
import org.snapscript.core.Type;

public class StaticInstance extends PrimitiveInstance {

   public StaticInstance(Stack stack, Module module, Model model, Scope scope, Type type, int key) {
      super(stack, module, model, scope, type, key);
   }

}
