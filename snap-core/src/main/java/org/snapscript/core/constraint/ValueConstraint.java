package org.snapscript.core.constraint;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class ValueConstraint extends Constraint {

   private final Value value;
   
   public ValueConstraint(Value value) {
      this.value = value;
   }
   
   @Override
   public Type getType(Scope scope){
      Module module = scope.getModule();
      Object object = value.getValue();
      
      if(value != null) {
         Class require = object.getClass();
         return module.getType(require); 
      }
      return null;
   }
}