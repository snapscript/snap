package org.snapscript.tree.define;

import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
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
         
         return new StaticInstance(module, model, inner, real); // create the first instance
      }
      return base;
   }
}
