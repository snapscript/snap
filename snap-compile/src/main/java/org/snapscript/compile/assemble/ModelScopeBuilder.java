package org.snapscript.compile.assemble;

import static org.snapscript.core.type.Phase.COMPILE;

import org.snapscript.common.Progress;
import org.snapscript.core.Context;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.ModuleRegistry;
import org.snapscript.core.scope.Model;
import org.snapscript.core.scope.ModelScope;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Phase;

public class ModelScopeBuilder {

   private final Context context;
   
   public ModelScopeBuilder(Context context) {
      this.context = context;
   }
  
   public Scope create(Model model, String name) {
      ModuleRegistry registry = context.getRegistry();
      Module module = registry.addModule(name);
      Progress<Phase> progress = module.getProgress();
      Scope outer = module.getScope();
      
      progress.done(COMPILE);

      return new ModelScope(model, module, outer);
   }
}