package org.snapscript.tree.constraint;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.type.TypeLoader;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.reference.TypeReference;

public class ArrayConstraint extends Constraint {

   private final TypeReference reference;
   private final StringToken[] bounds;
   
   public ArrayConstraint(TypeReference reference, StringToken... bounds) {
      this.reference = reference;
      this.bounds = bounds;
   }
   
   @Override
   public Type getType(Scope scope) {
      try {
         Value value = reference.evaluate(scope, null);
         Type entry = value.getValue();
         Module module = entry.getModule();
         Context context = module.getContext();
         TypeLoader loader = context.getLoader();
         String prefix = module.getName();
         String name = entry.getName();
         
         return loader.resolveArrayType(prefix, name, bounds.length);
      } catch(Exception e) {
         throw new InternalStateException("Invalid array constraint", e);
      }
   }
}