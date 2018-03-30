package org.snapscript.core.constraint;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;

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