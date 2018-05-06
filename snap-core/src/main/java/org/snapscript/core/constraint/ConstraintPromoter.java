package org.snapscript.core.constraint;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class ConstraintPromoter {
   
   private final Type type;
   
   public ConstraintPromoter(Type type) {
      this.type = type;
   }
   
   public Constraint promote(Constraint constraint) {
      Scope scope = type.getScope();
      Type type = constraint.getType(scope);
      
      if(type != null) {
         String name = constraint.getName(scope);
         Class real = type.getType();
         
         if(real == Object.class) {
            return new TypeConstraint(null, name);
         }
         if(real == void.class) {
            return new TypeConstraint(null, name);
         }
      }
      return constraint;
   }
}

