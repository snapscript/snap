package org.snapscript.tree;

import org.snapscript.core.Bug;
import org.snapscript.core.Constraint;
import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.Score;

public class DeclarationConverter {
   
   public DeclarationConverter() {
      super();
   }

   @Bug("we need some line numbers here")
   public Type compile(Scope scope, Type value, Constraint constraint, String name) throws Exception {
      if(value != null) {
         Type type = constraint.getType(scope);
         Module module = scope.getModule();
         Context context = module.getContext();
         ConstraintMatcher matcher = context.getMatcher();
         ConstraintConverter converter = matcher.match(type);
         Score score = converter.score(value);
         
         if(score.isInvalid()) {
            throw new InternalStateException("Variable '" + name + "' does not match constraint '" + type + "'");
         }
         return type;
      }
      return null;
   }
   
   public Object convert(Scope scope, Object value, Constraint constraint, String name) throws Exception {
      if(value != null) {
         Type type = constraint.getType(scope);
         Module module = scope.getModule();
         Context context = module.getContext();
         ConstraintMatcher matcher = context.getMatcher();
         ConstraintConverter converter = matcher.match(type);
         Score score = converter.score(value);
         
         if(score.isInvalid()) {
            throw new InternalStateException("Variable '" + name + "' does not match constraint '" + type + "'");
         }
         return converter.assign(value);
      }
      return null;
   }
}