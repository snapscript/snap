package org.snapscript.core.function.index;

import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.transform.ConstraintHandle;
import org.snapscript.core.constraint.transform.ConstraintTransform;
import org.snapscript.core.constraint.transform.ConstraintTransformer;
import org.snapscript.core.function.Function;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class ReturnTypeChecker {
   
   private final Function function;
   
   public ReturnTypeChecker(Function function) {
      this.function = function;
   }

   public Constraint check(Scope scope, Constraint left) {
      Constraint returns = function.getConstraint();
      String name = returns.getName(scope);
      
      if(name != null) {
         Type declared = function.getType();
         Type constraint = left.getType(scope);
         
         if(declared != null && constraint != null) {
            Module module = scope.getModule();
            Context context = module.getContext();         
            ConstraintTransformer transformer = context.getTransformer();
            ConstraintTransform transform = transformer.transform(constraint, declared);
            ConstraintHandle handle = transform.apply(left);
            
            return handle.getConstraint(name);     
         }
      }
      return returns;
   }
}
