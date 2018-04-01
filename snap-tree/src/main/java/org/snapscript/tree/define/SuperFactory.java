package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.result.Result;

public class SuperFactory extends TypeFactory {
   
   private final SuperInstanceBuilder builder;
   private final Evaluation expression;
   
   public SuperFactory(Evaluation expression, Type type) {
      this.builder = new SuperInstanceBuilder(type);
      this.expression = expression;
   }
   
   @Override
   public boolean define(Scope instance, Type real) throws Exception {
      expression.define(instance);
      return false;
   }
   
   @Override
   public void compile(Scope instance, Type real) throws Exception {
      Constraint constraint = Constraint.getVariable(real);
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