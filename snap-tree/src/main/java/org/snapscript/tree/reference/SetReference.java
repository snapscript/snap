package org.snapscript.tree.reference;

import static org.snapscript.core.constraint.Constraint.SET;

import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.StringToken;

public class SetReference extends ConstraintReference {
   
   public SetReference(StringToken token) {
      super();
   }
   
   @Override
   protected ConstraintValue create(Scope scope) {
      try {
         Type type = SET.getType(scope);
         Value reference = Value.getReference(type, type, SET);
         
         return new ConstraintValue(SET, reference, type);
      } catch(Exception e) {
         throw new InternalStateException("Could not resolve set reference", e);
      }
   }
}