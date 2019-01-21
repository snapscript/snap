package org.snapscript.core.variable.bind;

import static org.snapscript.core.ModifierType.CLASS;
import static org.snapscript.core.scope.index.AddressType.TYPE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.StaticConstraint;
import org.snapscript.core.scope.index.Address;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Constant;
import org.snapscript.core.variable.Value;

public class TypeResult implements VariableResult {
   
   private final Constraint constraint;
   private final Value value;
   private final String name;
   
   public TypeResult(Type type, String name) {
      this.constraint = new StaticConstraint(type, CLASS.mask);
      this.value = new Constant(type, constraint);
      this.name = name;
   }
   
   @Override
   public Address getAddress(int offset) {
      return TYPE.getAddress(name, offset);
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
