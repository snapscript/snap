package org.snapscript.tree.function;

import org.snapscript.core.Statement;
import org.snapscript.core.module.Module;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionBody;
import org.snapscript.core.function.FunctionType;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.StatementInvocationBuilder;

public class FunctionBuilder {

   protected final Statement statement;
   
   public FunctionBuilder(Statement statement) {
      this.statement = statement;
   }

   public FunctionBody create(Signature signature, Module module, Constraint constraint, String name) {
      Type type = new FunctionType(signature, module, null);
      InvocationBuilder builder = new StatementInvocationBuilder(signature, statement, constraint);
      Invocation invocation = new StatementInvocation(builder);
      Function function = new InvocationFunction(signature, invocation, type, constraint, name, 0);
      
      return new FunctionBody(builder, null, function);
   }
}