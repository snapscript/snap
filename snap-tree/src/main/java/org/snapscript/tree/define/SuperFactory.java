package org.snapscript.tree.define;

import org.snapscript.core.Constraint;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.Value;

public class SuperFactory extends TypeFactory {
   
   private final SuperInstanceBuilder builder;
   private final Evaluation expression;
   
   public SuperFactory(Evaluation expression, Type type) {
      this.builder = new SuperInstanceBuilder(type);
      this.expression = expression;
   }
   
   @Override
   public void define(Scope instance, Type real) throws Exception {
      expression.define(instance);
   }
   
   @Override
   public void compile(Scope instance, Type real) throws Exception {
      Constraint constraint = Constraint.getInstance(real);
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