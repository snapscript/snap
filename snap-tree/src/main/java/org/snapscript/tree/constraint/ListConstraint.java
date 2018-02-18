package org.snapscript.tree.constraint;

import java.util.List;

import org.snapscript.core.Constraint;
import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.parse.StringToken;

public class ListConstraint extends Constraint {

   private final StringToken token;
   
   public ListConstraint(StringToken token) {
      this.token = token;
   }
   
   @Override
   public Type getType(Scope scope) {
      try {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeLoader loader = context.getLoader();
         
         return loader.loadType(List.class);
      } catch(Exception e) {
         throw new InternalStateException("Invalid list constraint", e);
      }
   }
}