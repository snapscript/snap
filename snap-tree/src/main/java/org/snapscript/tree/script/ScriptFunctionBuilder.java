package org.snapscript.tree.script;

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
import org.snapscript.tree.function.FunctionBuilder;

public class ScriptFunctionBuilder extends FunctionBuilder {
   
   public ScriptFunctionBuilder(Statement statement) {
      super(statement);
   }

   @Override
   public FunctionBody create(Signature signature, Module module, Constraint constraint, String name) {
      Type type = new FunctionType(signature, module, null);
      InvocationBuilder builder = new StatementInvocationBuilder(signature, statement, constraint);
      Invocation invocation = new ScriptInvocation(builder, signature);
      Function function = new InvocationFunction(signature, invocation, type, constraint, name, 0);
      
      return new FunctionBody(builder, null, function);
   }
}