package org.snapscript.tree.closure;

import static org.snapscript.core.Reserved.METHOD_CLOSURE;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionType;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;

public class ClosureBuilder {

   private final Statement statement;
   private final Module module;
   
   public ClosureBuilder(Statement statement, Module module) {
      this.statement = statement;
      this.module = module;
   }

   public Function create(Signature signature, Scope scope) {
      return create(signature, scope, 0);
   }
   
   public Function create(Signature signature, Scope scope, int modifiers) {
      Type type = new FunctionType(signature, module, null);
      Invocation invocation = new ClosureInvocation(signature, statement, scope);
      
      return new InvocationFunction(signature, invocation, type, null, METHOD_CLOSURE, modifiers);
   }
}