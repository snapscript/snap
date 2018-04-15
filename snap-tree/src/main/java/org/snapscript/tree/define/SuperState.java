package org.snapscript.tree.define;

import static org.snapscript.core.type.Order.OTHER;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.Order;
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
   public Order define(Scope instance, Type real) throws Exception {
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
      Value reference = expression.evaluate(instance, real); 
      Scope value = reference.getValue();
      Scope base = builder.create(value, real);
      
      return Result.getNormal(base);
   }
}