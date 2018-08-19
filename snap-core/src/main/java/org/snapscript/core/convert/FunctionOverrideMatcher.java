package org.snapscript.core.convert;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.transform.ConstraintRule;
import org.snapscript.core.constraint.transform.ConstraintTransform;
import org.snapscript.core.constraint.transform.ConstraintTransformer;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class FunctionOverrideMatcher {

   private final ConstraintTransformer transformer;
   private final FunctionComparator comparator;

   public FunctionOverrideMatcher(ConstraintMatcher matcher, ConstraintTransformer transformer) {
      this.comparator = new FunctionComparator(matcher);
      this.transformer = transformer;
   }

   public List<Parameter> match(Scope scope, Function override, Function function) throws Exception {
      Type source = override.getSource();
      Constraint constraint = Constraint.getConstraint(source);
      ConstraintTransform transform = transformer.transform(source, function);
      ConstraintRule rule = transform.apply(constraint);
      Signature signature = override.getSignature();
      List<Parameter> left = signature.getParameters();
      List<Parameter> right = rule.getParameters(scope, function);
      Score score = comparator.compare(scope, left, right);

      return score.isSimilar() ? right : null;
   }
}
