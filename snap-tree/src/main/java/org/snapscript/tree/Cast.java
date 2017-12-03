package org.snapscript.tree;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.tree.constraint.Constraint;

public class Cast extends Evaluation {

   private final Evaluation evaluation;
   private final Constraint constraint;
   
   public Cast(Evaluation evaluation, Constraint constraint) {
      this.evaluation = evaluation;
      this.constraint = constraint;
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      evaluation.compile(scope);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Value value = constraint.evaluate(scope, left);
      Type type = value.getValue();
      Module module = scope.getModule();
      Context context = module.getContext();
      ConstraintMatcher matcher = context.getMatcher();
      ConstraintConverter converter = matcher.match(type);
      Object result = converter.convert(value);
      
      return Value.getTransient(result, type);
   }
}
