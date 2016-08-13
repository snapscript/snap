package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import org.snapscript.core.Context;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Instance;
import org.snapscript.core.define.PrimitiveInstance;

public class AnyInstanceBuilder {
   
   public AnyInstanceBuilder() {
      super();
   }

   public Instance create(Scope scope, Type real) throws Exception {
      Model model = scope.getModel();
      Module module = scope.getModule();
      Context context = module.getContext();
      ModuleRegistry registry = context.getRegistry();
      Module parent = registry.addModule(DEFAULT_PACKAGE);
      Scope inner = real.getScope();
      
      return new PrimitiveInstance(parent, model, inner, real); // create the first instance
   }
}
