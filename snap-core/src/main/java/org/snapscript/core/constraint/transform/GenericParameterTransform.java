package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class GenericParameterTransform implements ConstraintTransform {
   
   private final ConstraintIndex index;
   private final Constraint next;
   private final Type type;
   
   public GenericParameterTransform(ConstraintIndex index, Constraint next, Type type){
      this.index = index;
      this.next = next;
      this.type = type;
   }
   
   @Override
   public ConstraintRule apply(Constraint left){
      Scope scope = type.getScope();
      Constraint constraint = index.update(scope, left, next);
      
      if(constraint == null) {
         throw new InternalStateException("No constraint for '" + left + "'");
      }
      return new ConstraintIndexRule(constraint, index);
   }
}
