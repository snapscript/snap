package org.snapscript.tree.literal;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.constraint.Constraint;

public abstract class Literal extends Evaluation {
   
   private volatile Value value;
   
   protected Literal() {
      super();
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      if(value == null) {
         value = create(scope);
      }
      return value;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      if(value == null) {
         value = create(scope);
      }
      return value;
   }
   
   protected abstract LiteralValue create(Scope scope) throws Exception; 

   protected static class LiteralValue extends Value {
      
      private final Constraint constraint;
      private final Object value;
      
      public LiteralValue(Object value, Constraint constraint) {
         this.constraint = constraint;
         this.value = value;       
      }
      
      @Override
      public Type getType(Scope scope) {
         return constraint.getType(scope);
      }
      
      @Override
      public boolean isConstant() {
         return true;
      }      
      
      @Override
      public <T> T getValue() {
         return (T)value;
      }
      
      @Override
      public void setValue(Object value){
         throw new InternalStateException("Illegal modification of literal '" + value + "'");
      } 
      
      @Override
      public String toString() {
         return String.valueOf(value);
      }      
   }
}