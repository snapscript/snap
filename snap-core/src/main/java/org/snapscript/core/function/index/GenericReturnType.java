package org.snapscript.core.function.index;

import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.transform.ConstraintHandle;
import org.snapscript.core.constraint.transform.ConstraintTransform;
import org.snapscript.core.constraint.transform.ConstraintTransformer;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class GenericReturnType implements ReturnType {
   
   private final Constraint returns;
   private final Type declared;
   
   public GenericReturnType(Constraint returns, Type declared) {
      this.declared = declared;
      this.returns = returns;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      Type constraint = left.getType(scope);
      
      if(declared != null && constraint != null) {
         Module module = scope.getModule();
         Context context = module.getContext();         
         ConstraintTransformer transformer = context.getTransformer();
         ConstraintTransform transform = transformer.transform(constraint, declared);
         ConstraintHandle handle = transform.apply(left);
         
         return handle.getConstraint(returns);     
      }
      return left;
   }
}