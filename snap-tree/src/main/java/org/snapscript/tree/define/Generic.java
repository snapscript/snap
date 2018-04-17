package org.snapscript.tree.define;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class Generic extends Constraint {

   private final Constraint constraint;
   private final String name;
   
   public Generic(String name, Constraint constraint) {
      this.constraint = constraint;
      this.name = name;
   }
   
   @Override
   public Type getType(Scope scope) {     
      return constraint.getType(scope);
   }
   
   public String getName() {
      return name;
   }   
}
