package org.snapscript.tree.constraint;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class TypeConstraint extends Constraint {
   
   private List<Constraint> generics;
   private Constraint constraint;
   private String name;
   private Type type;
   
   public TypeConstraint(Constraint constraint) {
      this.constraint = constraint;
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope) {
      if(generics == null) {
         type = constraint.getType(scope);
         generics = constraint.getGenerics(scope);
         name = constraint.getName(scope);
      }
      return generics;
   }
   
   @Override
   public Type getType(Scope scope) {
      if(type == null) {
         type = constraint.getType(scope);
         generics = constraint.getGenerics(scope);
         name = constraint.getName(scope);
      }
      return type;
   }
   
   @Override
   public String getName(Scope scope) {
      if(name == null) {
         type = constraint.getType(scope);
         generics = constraint.getGenerics(scope);
         name = constraint.getName(scope);
      }
      return name;
   }

   @Override
   public String toString() {
      return String.valueOf(constraint);
   }
}
