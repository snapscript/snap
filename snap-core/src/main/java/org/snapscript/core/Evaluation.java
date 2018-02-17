package org.snapscript.core;

public class Evaluation{
   
   public void compile(Scope scope) throws Exception {}
   
   public Type validate(Scope scope, Type left) throws Exception {
      return null;
   }
   
   public Value evaluate(Scope scope, Object left) throws Exception {
      return Value.getNull();
   }
}