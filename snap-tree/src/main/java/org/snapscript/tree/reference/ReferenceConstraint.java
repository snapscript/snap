package org.snapscript.tree.reference;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.literal.Literal;

public class ReferenceConstraint extends Literal {

   private final Constraint constraint;

   public ReferenceConstraint(Constraint constraint) {
      this.constraint = constraint;
   }

   @Override
   protected Value create(Scope scope) throws Exception {
      Type type = constraint.getType(scope);
      
      if(type == null) {
         throw new InternalStateException("Could not find constraint");
      }
      return Value.getConstant(type);
   }
}