package org.snapscript.core;

public abstract class Evaluation{
   
   public void define(Scope scope) throws Exception {}
   
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      return Constraint.getNone();
   }

   public Value evaluate(Scope scope, Object left) throws Exception {
      return Value.getNull();
   }
}