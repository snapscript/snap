package org.snapscript.tree.literal;

import org.snapscript.core.Entity;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Data;
import org.snapscript.core.variable.DataMapper;
import org.snapscript.core.variable.Value;
import org.snapscript.core.variable.ValueData;

public abstract class Literal extends Evaluation {   
   
   private volatile LiteralValue value;
   
   protected Literal() {
      super();
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      if(value == null) {
         value = create(scope);
      }
      return value.constraint;
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      if(value == null) {
         value = create(scope);
      }
      return value;
   }
   
   protected abstract LiteralValue create(Scope scope) throws Exception; 

   protected static class LiteralValue extends Value {
      
      private final Constraint constraint;
      private final Entity source;
      private final Data value;
      
      public LiteralValue(Object value, Entity source, Constraint constraint) {
         this.value = new ValueData(value, source);
         this.constraint = constraint;
         this.source = source;     
      }
      
      @Override
      public boolean isConstant() {
         return true;
      }   
      
      @Override
      public Entity getSource() {
         return source;
      }
      
      @Override
      public Data getData() {
         return value;
      }   
      
      @Override
      public Constraint getConstraint() {
         return constraint;
      }     
      
      @Override
      public <T> T getValue() {
         return value.getValue();
      }
      
      @Override
      public void setData(Data value){
         throw new InternalStateException("Illegal modification of literal '" + value + "'");
      } 
      
      @Override
      public String toString() {
         return String.valueOf(value);
      }      
   }
}