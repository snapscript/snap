package org.snapscript.core.convert;

import java.util.Iterator;
import java.util.List;

import org.snapscript.core.ModifierType;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class AliasResolver {

   public AliasResolver() {
      super();
   }

   public Type resolve(Type type) {
      if(type != null) {
         Scope scope = type.getScope();
         int modifiers = type.getModifiers();

         if (ModifierType.isAlias(modifiers)) {
            List<Constraint> types = type.getTypes();
            Iterator<Constraint> iterator = types.iterator();

            if (iterator.hasNext()) {
               Constraint constraint = iterator.next();
               Type next = constraint.getType(scope);

               return resolve(next);
            }
         }
      }
      return type;
   }
}
