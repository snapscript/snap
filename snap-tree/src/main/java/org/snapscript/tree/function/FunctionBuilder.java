package org.snapscript.tree.function;

import org.snapscript.core.Module;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionType;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.StatementFunction;
import org.snapscript.tree.StatementConverter;

public class FunctionBuilder {

   private final Statement statement;
   
   public FunctionBuilder(Statement statement) {
      this.statement = statement;
   }

   public StatementFunction create(Signature signature, Module module, Type constraint, String name) {
      Type type = new FunctionType(signature, module, null);
      InvocationBuilder builder = new StatementConverter(signature, statement, statement, constraint);
      Invocation invocation = new StatementInvocation(builder, signature);
      Function function = new InvocationFunction(signature, invocation, type, constraint, name, 0);
      
      return new StatementFunction(builder, statement, function);
   }
}