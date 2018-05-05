package org.snapscript.core.function.index;

import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.transform.GenericReference;
import org.snapscript.core.constraint.transform.GenericTransform;
import org.snapscript.core.constraint.transform.GenericTransformer;
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
         Type require = left.getType(scope);
         
         if(declared != null && require != null) {
            Module module = scope.getModule();
            Context context = module.getContext();         
            GenericTransformer transformer = context.getTransformer();
            GenericTransform transform = transformer.transform(declared, require);
            GenericReference reference = transform.getReference(left);
            
            return reference.getConstraint(name);     
         }
      }
      return returns;
   }
}
