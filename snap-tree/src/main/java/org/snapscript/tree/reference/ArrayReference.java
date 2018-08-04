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

   private final Constraint constraint;
   private final StringToken[] bounds;
   
   public ArrayReference(Constraint constraint, StringToken... bounds) {
      this.constraint = constraint;
      this.bounds = bounds;
   }
   
   @Override
   protected ConstraintValue create(Scope scope) {
      try {
         Type entry = constraint.getType(scope);
         Module module = entry.getModule();
         Context context = module.getContext();
         TypeLoader loader = context.getLoader();
         String prefix = module.getName();
         String name = entry.getName();         
         Type array = loader.loadArrayType(prefix, name, bounds.length);
         Constraint constraint = Constraint.getConstraint(array);
         Value reference = Value.getReference(array, array, constraint);
         
         return new ConstraintValue(constraint, reference, array);
      } catch(Exception e) {
         throw new InternalStateException("Invalid array constraint", e);
      }
   }
}