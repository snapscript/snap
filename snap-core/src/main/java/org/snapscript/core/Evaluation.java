package org.snapscript.core;

import static org.snapscript.core.constraint.Constraint.NONE;
import static org.snapscript.core.variable.Value.NULL;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public abstract class Evaluation{
   
   public void define(Scope scope) throws Exception {}
   
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      return NONE;
   }

   public Value evaluate(Scope scope, Object left) throws Exception {
      return NULL;
   }
}