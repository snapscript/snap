package org.snapscript.tree;

import static org.snapscript.core.convert.Score.INVALID;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.Score;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.constraint.ConstraintExtractor;

public class DeclarationConverter {

   private final ConstraintExtractor extractor;

   public DeclarationConverter(Constraint constraint) {      
      this.extractor = new ConstraintExtractor(constraint);
   }   

   public Value convert(Scope scope, Object object, String name) throws Exception {
      Type type = extractor.extract(scope);
      
      if(type != null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         ConstraintMatcher matcher = context.getMatcher();
         ConstraintConverter converter = matcher.match(type);
         Score score = converter.score(object);
         
         if(score.compareTo(INVALID) == 0) {
            throw new InternalStateException("Variable '" + name + "' does not match constraint '" + type + "'");
         }
         if(object != null) {
            object = converter.convert(object);
         }
      }
      return ValueType.getTransient(object, type);
   }
}
