package org.snapscript.core.constraint.transform;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class ConstraintSource {

   private final Type type;

   public ConstraintSource(Type type) {
      this.type = type;
   }

   public List<Constraint> getConstraints(Constraint constraint) {
      Scope scope = type.getScope();
      List<Constraint> generics = constraint.getGenerics(scope);
      List<Constraint> constraints = type.getConstraints();
      int require = constraints.size();
      int actual = generics.size();

      if(actual > 0) { // the generics were declared
         if(require != actual) {
            throw new InternalStateException("Invalid generic parameters for " + type);
         }
         return generics;
      }
      return constraints; // no generics declared
   }
}
