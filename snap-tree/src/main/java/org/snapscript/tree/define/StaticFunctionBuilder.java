package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.FunctionHandle;
import org.snapscript.tree.StatementBlock;
import org.snapscript.tree.StatementInvocationBuilder;

public class StaticFunctionBuilder implements MemberFunctionBuilder {
   
   private final Signature signature;
   private final Statement body;
   private final Type constraint;
   private final String name;
   private final int modifiers;

   public StaticFunctionBuilder(Signature signature, Statement body, Type constraint, String name, int modifiers) {
      this.constraint = constraint;
      this.signature = signature;
      this.modifiers = modifiers;
      this.body = body;
      this.name = name;
   }
   
   @Override
   public FunctionHandle create(TypeFactory factory, Scope scope, Type type){
      Statement initialize = new StaticBody(factory, type); 
      Statement statement = new StatementBlock(initialize, body); 
      InvocationBuilder builder = new StatementInvocationBuilder(signature, body, statement, constraint);
      Invocation invocation = new StaticInvocation(builder, scope);
      Function function = new InvocationFunction(signature, invocation, type, constraint, name, modifiers);
      
      return new FunctionHandle(builder, function, body);
   }
}