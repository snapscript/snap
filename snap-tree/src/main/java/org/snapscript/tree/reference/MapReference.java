package org.snapscript.tree.reference;

import static org.snapscript.core.constraint.Constraint.MAP;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.parse.StringToken;

public class MapReference extends ConstraintReference {
   
   public MapReference(StringToken token) {
      super();
   }
   
   @Override
   protected ConstraintValue create(Scope scope) {
      try {
         Type type = MAP.getType(scope);
         return new ConstraintValue(type, MAP);
      } catch(Exception e) {
         throw new InternalStateException("Could not resolve map reference", e);
      }
   }
}