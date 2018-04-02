package org.snapscript.core;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Type;

public class Identity extends Evaluation {
   
   private final Object value;
   private final Type type;
   
   public Identity(Object value) {
      this(value, null);      
   }
   
   public Identity(Object value, Type type) {
      this.value = value;
      this.type = type;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      return Constraint.getConstraint(type);
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      return Value.getTransient(value, type);
   }
}