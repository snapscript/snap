package org.snapscript.tree.reference;

import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeLoader;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.StringToken;

public class ArrayReference extends ConstraintReference {

   private final TypeReference reference;
   private final StringToken[] bounds;
   
   public ArrayReference(TypeReference reference, StringToken... bounds) {
      this.reference = reference;
      this.bounds = bounds;
   }
   
   @Override
   protected ConstraintValue create(Scope scope) {
      try {
         Value value = reference.evaluate(scope, null);
         Type entry = value.getValue();
         Module module = entry.getModule();
         Context context = module.getContext();
         TypeLoader loader = context.getLoader();
         String prefix = module.getName();
         String name = entry.getName();         
         Type array = loader.loadArrayType(prefix, name, bounds.length);
         Constraint constraint = Constraint.getConstraint(array);
         
         return new ConstraintValue(array, constraint);
      } catch(Exception e) {
         throw new InternalStateException("Invalid array constraint", e);
      }
   }
}