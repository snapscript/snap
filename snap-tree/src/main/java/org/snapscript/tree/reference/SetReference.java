package org.snapscript.tree.reference;

import static org.snapscript.core.constraint.Constraint.SET;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.parse.StringToken;

public class SetReference extends ConstraintReference {
   
   public SetReference(StringToken token) {
      super();
   }
   
   @Override
   protected ConstraintValue create(Scope scope) {
      try {
         Type type = SET.getType(scope);
         return new ConstraintValue(type, SET);
      } catch(Exception e) {
         throw new InternalStateException("Could not resolve set reference", e);
      }
   }
}