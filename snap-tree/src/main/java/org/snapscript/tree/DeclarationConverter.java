package org.snapscript.tree;

import static org.snapscript.core.convert.Score.INVALID;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.ModifierType;
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
   private final Evaluation expression;
   
   public DeclarationConverter(Constraint constraint, Evaluation expression) {      
      this.extractor = new ConstraintExtractor(constraint);
      this.expression = expression;
   }   

   public Value convert(Scope scope, String name, int modifiers) throws Exception {
      Type type = extractor.extract(scope);
      Object object = null;
      
      if(expression != null) {
         Value value = expression.evaluate(scope, null);
         object = value.getValue();
      }
      if(type != null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         ConstraintMatcher matcher = context.getMatcher();
         ConstraintConverter converter = matcher.match(type);
         
         if(object != null) {
            Score score = converter.score(object);
            
            if(score.compareTo(INVALID) == 0) {
               throw new InternalStateException("Variable '" + name + "' does not match constraint '" + type + "'");
            }
            if(object != null) {
               object = converter.convert(object);
            }
         }
      }
      return create(scope, object, type, modifiers);
   }
   
   protected Value create(Scope scope, Object value, Type type, int modifiers) throws Exception {
      if(ModifierType.isConstant(modifiers)) {
         return ValueType.getConstant(value, type);
      }
      return ValueType.getReference(value, type);
   }
}
