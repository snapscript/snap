package org.snapscript.core.type.index;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.scope.instance.PrimitiveInstance;
import org.snapscript.core.type.Type;

public class PrimitiveInstanceBuilder {
   
   private final PrimitiveTypeResolver resolver;
   
   public PrimitiveInstanceBuilder() {
      this.resolver = new PrimitiveTypeResolver();
   }

   public Instance create(Scope scope, Type real) throws Exception {
      Scope inner = real.getScope();
      Type type = resolver.resolve(inner);
      Module module = type.getModule();
      
      return new PrimitiveInstance(module, inner, real, type); 
   }
}