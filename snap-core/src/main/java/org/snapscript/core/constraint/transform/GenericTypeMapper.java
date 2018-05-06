package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.TypeConstraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class GenericTypeMapper {
   
   private final Type type;
   
   public GenericTypeMapper(Type type) {
      this.type = type;
   }
   
   public Constraint map(Constraint constraint) {
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
         return constraint;
      }
      return constraint;
   }
}

