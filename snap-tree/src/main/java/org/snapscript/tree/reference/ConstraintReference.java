package org.snapscript.tree.reference;

import java.util.List;

import org.snapscript.core.Entity;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.ConstraintDescription;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Data;
import org.snapscript.core.variable.DataMapper;
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
      return value.constraint;
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      if(value == null) {
         value = create(scope);
      }
      return value;
   }
   
   protected abstract ConstraintValue create(Scope scope) throws Exception;
   
   protected static class ConstraintValue extends Value {
      
      private final Constraint constraint;
      private final Entity source;
      private final Value value;
      
      public ConstraintValue(Constraint constraint, Value value, Entity source) {
         this.constraint = new ConstraintDefinition(constraint, source);
         this.source = source;
         this.value = value;       
      }      

      @Override
      public Entity getSource() {
         return source;
      }  
      
      @Override
      public Data getData() {
         return DataMapper.toData(this);
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
         return value.toString();
      }    
   }   

   protected static class ConstraintDefinition extends Constraint {
      
      private ConstraintDescription description;
      private List<Constraint> generics;
      private List<String> imports;
      private Constraint constraint;
      private Type type;
      private String name;
      
      public ConstraintDefinition(Constraint constraint, Entity source) {
         this.description = new ConstraintDescription(constraint, source);
         this.constraint = constraint;      
      }
      
      @Override
      public List<String> getImports(Scope scope) {
         if(imports == null) {
            imports = constraint.getImports(scope);
         }
         return imports;
      }
      
      @Override
      public List<Constraint> getGenerics(Scope scope) {
         if(generics == null) {
            generics = constraint.getGenerics(scope);
         }
         return generics;
      }
      
      @Override
      public Type getType(Scope scope) {
         if(type == null) {
            type = constraint.getType(scope);
         }
         return type;
      }
      
      @Override
      public String getName(Scope scope) {
         if(name == null) {
            name = constraint.getName(scope);
         }
         return name;
      }      

      @Override
      public String toString() {
         return description.toString();
      }      
   }
}
