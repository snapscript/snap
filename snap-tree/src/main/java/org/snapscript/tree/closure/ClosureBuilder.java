package org.snapscript.tree.closure;

import static org.snapscript.core.Reserved.METHOD_CLOSURE;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
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

public class ClosureBuilder {

   private final Statement statement;
   private final Module module;
   
   public ClosureBuilder(Statement statement, Module module) {
      this.statement = statement;
      this.module = module;
   }

   public StatementFunction create(Signature signature, Scope scope) {
      return create(signature, scope, 0);
   }
   
   public StatementFunction create(Signature signature, Scope scope, int modifiers) {
      Type type = new FunctionType(signature, module, null);
      InvocationBuilder builder = new StatementConverter(signature, statement, statement, null, true);
      Invocation invocation = new ClosureInvocation(builder, signature, scope);
      Function function = new InvocationFunction(signature, invocation, type, null, METHOD_CLOSURE, modifiers);
      
      return new StatementFunction(builder, null, function);
   }
}