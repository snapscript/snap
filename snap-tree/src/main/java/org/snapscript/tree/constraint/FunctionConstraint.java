package org.snapscript.tree.constraint;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.function.ParameterList;

public class FunctionConstraint extends Constraint {

   private final ParameterList list;
   
   public FunctionConstraint(ParameterList list) {
      this.list = list;
   }
   
   @Override
   public Type getType(Scope scope) {
      try {
         Signature signature = list.create(scope);
         return signature.getDefinition();
      } catch(Exception e) {
         throw new InternalStateException("Invalid function constraint", e);
      }
   }
}