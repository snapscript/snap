package org.snapscript.tree.define;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class GenericParameter extends Constraint {

   private final Constraint constraint;
   private final String name;
   
   public GenericParameter(String name, Constraint constraint) {
      this.constraint = constraint;
      this.name = name;
   }
   
   @Override
   public Type getType(Scope scope) {     
      return constraint.getType(scope);
   }
   
   @Override
   public String getName() {
      return name;
   }   
}
