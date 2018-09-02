package org.snapscript.core.attribute;

import static org.snapscript.core.error.Reason.GENERIC;

import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.transform.ConstraintRule;
import org.snapscript.core.constraint.transform.ConstraintTransform;
import org.snapscript.core.constraint.transform.ConstraintTransformer;
import org.snapscript.core.convert.Score;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.ArgumentConverterBuilder;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class GenericAttributeResult implements AttributeResult {

   private final ArgumentConverterBuilder builder;
   private final Attribute attribute;

   public GenericAttributeResult(Attribute attribute) {
      this.builder = new ArgumentConverterBuilder();
      this.attribute = attribute;      
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left, Type... types) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();  
      Type constraint = left.getType(scope);
      Constraint returns = attribute.getConstraint();       
      ConstraintTransformer transformer = context.getTransformer();
      ConstraintTransform transform = transformer.transform(constraint, attribute);
      ConstraintRule rule = transform.apply(left);

      if(Function.class.isInstance(attribute)) {
         Function function = (Function)attribute;
         List<Parameter> parameters = rule.getParameters(scope, function);
         ArgumentConverter converter = builder.create(scope, parameters);
         ErrorHandler handler = context.getHandler();
         Score score = converter.score(types);
         String name = function.getName();

         if(score.isInvalid()) {
            handler.handleCompileError(GENERIC, scope, name, types);
         }
      }
      return rule.getResult(scope, returns);
   }
}