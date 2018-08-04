package org.snapscript.tree.define;

import static org.snapscript.core.type.Category.OTHER;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class SuperState extends TypeState {
   
   private final SuperInstanceBuilder builder;
   private final Evaluation expression;
   
   public SuperState(Evaluation expression, Type type) {
      this.builder = new SuperInstanceBuilder(type);
      this.expression = expression;
   }
   
   @Override
   public Category define(Scope instance, Type real) throws Exception {
      expression.define(instance);
      return OTHER;
   }
   
   @Override
   public void compile(Scope instance, Type real) throws Exception {
      Constraint constraint = Constraint.getConstraint(real);
      expression.compile(instance, constraint);
   }

   @Override
   public Result execute(Scope instance, Type real) throws Exception {
      Value value = Value.getTransient(real, real);
      Value reference = expression.evaluate(instance, value); 
      Scope scope = reference.getValue();
      Scope base = builder.create(scope, value);
      
      return Result.getNormal(base);
   }
}