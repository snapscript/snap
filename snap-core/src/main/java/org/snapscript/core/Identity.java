package org.snapscript.core;

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
      return Constraint.getInstance(type);
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      return Value.getTransient(value, type);
   }
}