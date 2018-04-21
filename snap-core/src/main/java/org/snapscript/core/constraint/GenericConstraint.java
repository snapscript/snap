package org.snapscript.core.constraint;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class GenericConstraint extends Constraint {

   private final Constraint constraint;
   private final String name;
   
   public GenericConstraint(String name, Constraint constraint) {
      this.constraint = constraint;
      this.name = name;
   }
   
   @Override
   public Type getType(Scope scope) {     
      return constraint.getType(scope);
   }
   
   @Override
   public String getName(Scope scope) {
      return name;
   }   
}
