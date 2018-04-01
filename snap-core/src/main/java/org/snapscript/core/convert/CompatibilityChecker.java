package org.snapscript.core.convert;

import org.snapscript.core.Context;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class CompatibilityChecker {
   
   public CompatibilityChecker() {
      super();
   }

   public boolean compatible(Scope scope, Object value, String name) throws Exception {
      if(name != null) {
         Module module = scope.getModule();
         Type type = module.getType(name);

         return compatible(scope, value, type);
      }
      return true;
   }

   public boolean compatible(Scope scope, Object value, Type type) throws Exception {
      if(type != null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         ConstraintMatcher matcher = context.getMatcher();
         ConstraintConverter converter = matcher.match(type);
         Score score = converter.score(value);
         
         return score.isValid();
      }
      return true;
   }
}