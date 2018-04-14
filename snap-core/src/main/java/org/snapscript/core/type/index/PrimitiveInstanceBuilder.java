package org.snapscript.core.type.index;

import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import org.snapscript.core.Context;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.ModuleRegistry;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.scope.instance.PrimitiveInstance;
import org.snapscript.core.type.Type;

public class PrimitiveInstanceBuilder {
   
   private Module module;
   
   public PrimitiveInstanceBuilder() {
      super();
   }

   public Instance create(Scope scope, Type real) throws Exception {
      Scope inner = real.getScope();
      
      if(module == null) {
         Module parent = scope.getModule();
         Context context = parent.getContext();
         ModuleRegistry registry = context.getRegistry();
         
         module = registry.addModule(DEFAULT_PACKAGE);
      }
      return new PrimitiveInstance(module, inner, real); 
   }
}