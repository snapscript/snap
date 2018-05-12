package org.snapscript.tree.constraint;

import java.util.List;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class TypeConstraint extends Constraint {

   private Evaluation evaluation;
   private Constraint constraint;
   
   public TypeConstraint(Evaluation evaluation) {
      this.evaluation = evaluation;
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope) {
      if(constraint == null) {
         try {
            constraint = evaluation.compile(scope, null);
         } catch (Exception e) {
            throw new InternalStateException("Import not found", e);
         }
      }
      return constraint.getGenerics(scope);
   }
   
   @Override
   public Type getType(Scope scope) {
      if(constraint == null) {
         try {
            constraint = evaluation.compile(scope, null);
         } catch (Exception e) {
            throw new InternalStateException("Import not found", e);
         }
      }
      return constraint.getType(scope);
   }
   
   @Override
   public String getName(Scope scope) {
      if(constraint == null) {
         try {
            constraint = evaluation.compile(scope, null);
         } catch (Exception e) {
            throw new InternalStateException("Import not found", e);
         }
      }
      return constraint.getName(scope);
   }
   
   @Override
   public String toString() {
      return String.valueOf(constraint);
   }
}
