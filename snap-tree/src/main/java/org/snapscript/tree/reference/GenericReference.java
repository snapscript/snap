package org.snapscript.tree.reference;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public class GenericReference extends ConstraintReference {
   
   private final GenericDeclaration declaration;
   
   public GenericReference(TypeReference type) {
      this(type, null);
   }
   
   public GenericReference(TypeReference type, GenericArgumentList list) {
      this.declaration = new GenericDeclaration(type, list);
   }

   @Override
   protected ConstraintValue create(Scope scope) throws Exception {
      Value value = declaration.declare(scope);
      Module module = scope.getModule();
      
      return new ConstraintValue(value, value, module);
   }      
}