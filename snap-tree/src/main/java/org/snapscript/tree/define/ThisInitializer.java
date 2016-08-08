package org.snapscript.tree.define;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.define.Instance;

public class ThisInitializer extends Initializer {
   
   private final Evaluation expression;
   private final Statement statement;
   private final AtomicBoolean done;
   
   public ThisInitializer(Statement statement, Evaluation expression) {
      this.done = new AtomicBoolean();
      this.expression = expression;
      this.statement = statement;
   }

   @Override
   public Result execute(Scope instance, Type real) throws Exception {
      if(done.compareAndSet(false, true)) {
         statement.compile(instance);
      }
      return create(instance, real);
   }

   private Result create(Scope scope, Type real) throws Exception {
      Scope inner = scope.getInner();
      Value value = expression.evaluate(inner, null);
      Instance result = value.getValue();
      
      return ResultType.getNormal(result); // this will return the instance created!!
   }
}