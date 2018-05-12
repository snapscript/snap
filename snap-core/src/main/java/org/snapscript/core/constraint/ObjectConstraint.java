package org.snapscript.core.constraint;

import org.snapscript.core.Context;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeLoader;

public class ObjectConstraint extends Constraint {

   private final Object object;
   
   public ObjectConstraint(Object object) {
      this.object = object;
   }
   
   @Override
   public Type getType(Scope scope){
      if(object != null) {
         Class require = object.getClass();
         Module module = scope.getModule();
         Context context = module.getContext();               
         TypeLoader loader = context.getLoader();
         
         return loader.loadType(require);
      }
      return null;
   }
}