package org.snapscript.core;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public class IdentityEvaluation extends Evaluation {
   
   private final Constraint type;
   private final Object value;
   
   public IdentityEvaluation(Object value) {
      this(value, NONE);      
   }
   
   public IdentityEvaluation(Object value, Constraint type) {
      this.value = value;
      this.type = type;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      return type;
   }

   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      return Value.getTransient(value, type);
   }
}