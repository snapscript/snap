package org.snapscript.core.constraint;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class ConstraintMapper {
   
   public ConstraintMapper() {
      super();
   }
   
   public Constraint map(Scope scope, Constraint constraint) {    
      Type type = constraint.getType(scope);
      
      if(type != null) {
         String name = constraint.getName(scope);
         Class real = type.getType();
         
         if(real == Object.class) {
            return new TypeParameterConstraint(null, name);
         }
         if(real == void.class) {
            return new TypeParameterConstraint(null, name);
         }
      }
      return constraint;
   }
}

