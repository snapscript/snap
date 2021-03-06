package org.snapscript.tree.reference;

import static java.util.Collections.EMPTY_LIST;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.function.Signature;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.function.ParameterList;

public class FunctionReference extends ConstraintReference {
   
   private final ParameterList list;
   
   public FunctionReference(ParameterList list) {
      this.list = list;
   }
   
   @Override
   protected ConstraintValue create(Scope scope) {
      try {
         Signature signature = list.create(scope, EMPTY_LIST);
         Type type = signature.getDefinition();
         Constraint constraint = Constraint.getConstraint(type);
         Value reference = Value.getReference(type);
         
         return new ConstraintValue(constraint, reference, type);
      } catch(Exception e) {
         throw new InternalStateException("Invalid function reference", e);
      }
   }
}