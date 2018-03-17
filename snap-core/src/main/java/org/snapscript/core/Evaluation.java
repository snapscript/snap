package org.snapscript.core;

public abstract class Evaluation{
   
   public void compile(Scope scope) throws Exception {}
   
   public Constraint validate(Scope scope, Constraint left) throws Exception {
      return Constraint.getNone();
   }

   public Value evaluate(Scope scope, Object left) throws Exception {
      return Value.getNull();
   }
}