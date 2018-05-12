package org.snapscript.core.constraint;

import org.snapscript.core.Context;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeLoader;
import org.snapscript.core.variable.Value;

public class ValueConstraint extends Constraint {

   private final Value value;
   
   public ValueConstraint(Value value) {
      this.value = value;
   }
   
   @Override
   public Type getType(Scope scope){
      Object object = value.getValue();
      
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