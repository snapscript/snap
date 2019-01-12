package org.snapscript.core.type.index;

import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.TypeNameConstraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.scope.instance.PrimitiveInstance;
import org.snapscript.core.type.Type;

public class PrimitiveInstanceBuilder {
   
   private final Constraint constraint;
   
   public PrimitiveInstanceBuilder() {
      this.constraint = new TypeNameConstraint(DEFAULT_PACKAGE, ANY_TYPE);
   }

   public Instance create(Scope scope, Type real) throws Exception {
      Scope inner = real.getScope();
      Type type = constraint.getType(inner);
      Module module = type.getModule();
      
      return new PrimitiveInstance(module, inner, real, type); 
   }
}