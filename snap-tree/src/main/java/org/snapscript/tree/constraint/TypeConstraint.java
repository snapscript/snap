package org.snapscript.tree.constraint;

import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.tree.reference.ConstraintReference;

public class TypeConstraint extends Constraint {

   private ConstraintReference reference;
   private Constraint constraint;
   
   public TypeConstraint(ConstraintReference reference) {
      this.reference = reference;
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope) {
      if(constraint == null) {
         try {
            constraint = reference.compile(scope, null);
         } catch (Exception e) {
            throw new InternalStateException("Could not resolve constraint", e);
         }
      }
      return constraint.getGenerics(scope);
   }
   
   @Override
   public Type getType(Scope scope) {
      if(constraint == null) {
         try {
            constraint = reference.compile(scope, null);
         } catch (Exception e) {
            throw new InternalStateException("Could not resolve constraint", e);
         }
      }
      return constraint.getType(scope);
   }
   
   @Override
   public String getName(Scope scope) {
      if(constraint == null) {
         try {
            constraint = reference.compile(scope, null);
         } catch (Exception e) {
            throw new InternalStateException("Could not resolve constraint", e);
         }
      }
      return constraint.getName(scope);
   }
   
   @Override
   public String toString() {
      return String.valueOf(constraint);
   }
}
