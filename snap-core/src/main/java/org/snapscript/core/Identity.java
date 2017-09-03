package org.snapscript.core;

public class Identity extends Evaluation {
   
   private final Object value;
   
   public Identity(Object value) {
      this.value = value;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      return ValueType.getTransient(value);
   }
}