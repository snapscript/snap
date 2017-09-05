package org.snapscript.core;

public class Evaluation{
   
   public void compile(Scope scope) throws Exception {}
   
   public Value evaluate(Scope scope, Object left) throws Exception {
      return Value.getNull();
   }
}