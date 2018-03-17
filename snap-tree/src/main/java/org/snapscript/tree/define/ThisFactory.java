package org.snapscript.tree.define;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.Value;
import org.snapscript.core.define.Instance;

public class ThisFactory extends TypeFactory {
   
   private final AtomicBoolean execute;
   private final AtomicBoolean compile;
   private final AtomicBoolean validate;
   private final Evaluation expression;
   private final Statement statement;
   
   public ThisFactory(Statement statement, Evaluation expression) {
      this.compile = new AtomicBoolean();
      this.execute = new AtomicBoolean();
      this.validate = new AtomicBoolean();
      this.expression = expression;
      this.statement = statement;
   }

   @Override
   public void compile(Scope instance, Type real) throws Exception {
      if(compile.compareAndSet(false, true)) {
         expression.define(instance);
      }
   }
   
   @Override
   public void validate(Scope instance, Type real) throws Exception {
      if(validate.compareAndSet(false, true)) {
         expression.compile(instance, null);
         statement.compile(instance);
      }
   }   
   
   @Override
   public Result execute(Scope instance, Type real) throws Exception {
      if(execute.compareAndSet(false, true)) {
         statement.define(instance);
      }
      return create(instance, real);
   }

   private Result create(Scope scope, Type real) throws Exception {
      Value value = expression.evaluate(scope, null);
      Instance result = value.getValue();
      
      return Result.getNormal(result); // this will return the instance created!!
   }
}