package org.snapscript.tree.reference;

import static org.snapscript.core.constraint.Constraint.LIST;

import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.StringToken;

public class ListReference extends ConstraintReference {
   
   public ListReference(StringToken token) {
      super();
   }
   
   @Override
   protected ConstraintValue create(Scope scope) {
      try {
         Type type = LIST.getType(scope);
         Value reference = Value.getReference(type, type, LIST);
         
         return new ConstraintValue(LIST, reference, type);
      } catch(Exception e) {
         throw new InternalStateException("Could not resolve list reference", e);
      }
   }
}