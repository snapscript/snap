package org.snapscript.tree.reference;

import static org.snapscript.core.constraint.Constraint.OBJECT;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.parse.StringToken;

public class GenericArgument {
   
   private final Constraint constraint;
   
   public GenericArgument(StringToken token) {
      this(OBJECT, null);
   }
   
   public GenericArgument(Constraint constraint) {
      this(constraint, null);
   }
   
   public GenericArgument(Constraint constraint, StringToken token) {
      this.constraint = constraint;
   }
   
   public Constraint getConstraint() {
      return constraint;
   }

}
