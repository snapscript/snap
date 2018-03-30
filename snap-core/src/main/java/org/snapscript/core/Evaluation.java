package org.snapscript.core;

import static org.snapscript.core.Value.NULL;
import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.constraint.Constraint;

public abstract class Evaluation{
   
   public void define(Scope scope) throws Exception {}
   
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      return NONE;
   }

   public Value evaluate(Scope scope, Object left) throws Exception {
      return NULL;
   }
}