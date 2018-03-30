package org.snapscript.tree.constraint;

import java.util.Set;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.parse.StringToken;

public class SetConstraint extends Constraint {

   private final StringToken token;
   
   public SetConstraint(StringToken token) {
      this.token = token;
   }
   
   @Override
   public Type getType(Scope scope) {
      try {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeLoader loader = context.getLoader();
         
         return loader.loadType(Set.class);
      } catch(Exception e) {
         throw new InternalStateException("Invalid map constraint", e);
      }
   }
}