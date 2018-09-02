package org.snapscript.core.function.index;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.attribute.AttributeResult;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class ReturnType {

   private final AttributeResult result;
   private final Scope scope;

   public ReturnType(AttributeResult result, Scope scope) {
      this.result = result;
      this.scope = scope;
   }

   public Constraint check(Constraint left, Type[] types) throws Exception {
      if(result != null) {
         return result.getConstraint(scope, left, types);
      }
      return NONE;
   }
}
