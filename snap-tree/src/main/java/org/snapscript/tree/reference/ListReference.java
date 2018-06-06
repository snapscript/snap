package org.snapscript.tree.reference;

import static org.snapscript.core.constraint.Constraint.LIST;

import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.parse.StringToken;

public class ListReference extends ConstraintReference {
   
   public ListReference(StringToken token) {
      super();
   }
   
   @Override
   protected ConstraintValue create(Scope scope) {
      try {
         Type type = LIST.getType(scope);
         return new ConstraintValue(type, LIST);
      } catch(Exception e) {
         throw new InternalStateException("Could not resolve list reference", e);
      }
   }
}