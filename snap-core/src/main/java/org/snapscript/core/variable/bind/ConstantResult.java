package org.snapscript.core.variable.bind;

import org.snapscript.core.Entity;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.variable.Constant;
import org.snapscript.core.variable.Value;

public class ConstantResult implements VariableResult {
   
   private final Constraint constraint;
   private final Value value;
   
   public ConstantResult(Object value, Entity source, Constraint constraint) {
      this.value = new Constant(value, source);
      this.constraint = constraint;
   }

   @Override
   public Constraint getConstraint(Constraint left) {
      return constraint;
   }

   @Override
   public Value getValue(Object left) {
      return value;
   }

}
