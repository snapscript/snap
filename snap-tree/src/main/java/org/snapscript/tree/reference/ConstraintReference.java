package org.snapscript.tree.reference;

import java.util.List;

import org.snapscript.core.Entity;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.ConstraintDescription;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public abstract class ConstraintReference extends Evaluation {

   private volatile ConstraintValue value;
   
   protected ConstraintReference() {
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
   
   protected abstract ConstraintValue create(Scope scope) throws Exception;

   protected static class ConstraintValue extends Value {
      
      private final ConstraintDescription description;
      private final Constraint constraint;
      private final Value value;
      
      public ConstraintValue(Constraint constraint, Value value, Entity entity) {
         this.description = new ConstraintDescription(constraint, entity);
         this.constraint = constraint;
         this.value = value;       
      }
      
      @Override
      public List<String> getImports(Scope scope) {
         return constraint.getImports(scope);
      }
      
      @Override
      public List<Constraint> getGenerics(Scope scope) {
         return constraint.getGenerics(scope);
      }
      
      @Override
      public Type getType(Scope scope) {
         return constraint.getType(scope);
      }
      
      @Override
      public String getName(Scope scope) {
         return constraint.getName(scope);
      }       
      
      @Override
      public <T> T getValue() {
         return value.getValue();
      }
      
      @Override
      public void setValue(Object value){
         throw new InternalStateException("Illegal modification of literal '" + value + "'");
      } 
      
      @Override
      public String toString() {
         return description.toString();
      }      
   }
}
