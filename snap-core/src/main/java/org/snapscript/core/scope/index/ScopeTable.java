package org.snapscript.core.scope.index;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.variable.Value;

public interface ScopeTable extends Iterable<Value> {
   Value getValue(Address address);
   Constraint getConstraint(Address address);
   void addValue(Address address, Value value);
   void addConstraint(Address address, Constraint constraint);
}
