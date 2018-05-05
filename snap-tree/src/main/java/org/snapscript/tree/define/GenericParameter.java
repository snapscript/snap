package org.snapscript.tree.define;

import java.util.List;

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
   public List<Constraint> getGenerics(Scope scope) {     
      return constraint.getGenerics(scope);
   }
   
   @Override
   public Type getType(Scope scope) {     
      return constraint.getType(scope);
   }
   
   @Override
   public String getName(Scope scope) {
      return name;
   }
   
   @Override
   public String toString() {
      return name;
   }
   
}
