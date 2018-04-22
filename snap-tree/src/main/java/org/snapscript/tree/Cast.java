package org.snapscript.tree;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.tree.constraint.DeclarationConstraint;

public class Cast extends Evaluation {

   private final Constraint constraint;
   private final Evaluation evaluation;
   
   public Cast(Evaluation evaluation, Constraint constraint) {
      this.constraint = new DeclarationConstraint(constraint);
      this.evaluation = evaluation;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      evaluation.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) {
      return constraint;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Value value = evaluation.evaluate(scope, left);
      Type type = constraint.getType(scope);
      Object object = value.getValue();
      Module module = scope.getModule();
      Context context = module.getContext();
      ConstraintMatcher matcher = context.getMatcher();
      ConstraintConverter converter = matcher.match(type);
      Object result = converter.convert(object);
      
      return Value.getTransient(result, constraint);
   }
}
