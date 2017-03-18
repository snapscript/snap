
package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.define.Initializer;

public class SuperInitializer extends Initializer {
   
   private final SuperInstanceBuilder builder;
   private final Evaluation expression;
   
   public SuperInitializer(Evaluation expression, Type type) {
      this.builder = new SuperInstanceBuilder(type);
      this.expression = expression;
   }

   @Override
   public Result execute(Scope instance, Type real) throws Exception {
      Value reference = expression.evaluate(instance, real); 
      Scope value = reference.getValue();
      Scope base = builder.create(value, real);
      
      return ResultType.getNormal(base);
   }
}