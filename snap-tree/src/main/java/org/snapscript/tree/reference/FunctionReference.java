package org.snapscript.tree.reference;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Signature;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.tree.function.ParameterList;
import org.snapscript.tree.literal.Literal;

public class FunctionReference extends Literal {
   
   private final ParameterList list;
   
   public FunctionReference(ParameterList list) {
      this.list = list;
   }
   
   @Override
   protected LiteralValue create(Scope scope) {
      try {
         Signature signature = list.create(scope);
         Type type = signature.getDefinition();
         Constraint constraint = Constraint.getConstraint(type);
         
         return new LiteralValue(constraint, NONE);
      } catch(Exception e) {
         throw new InternalStateException("Invalid function reference", e);
      }
   }
}