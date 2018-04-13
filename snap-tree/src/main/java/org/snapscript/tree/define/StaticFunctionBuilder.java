package org.snapscript.tree.define;

import org.snapscript.core.Execution;
import org.snapscript.core.Statement;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionBody;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;
import org.snapscript.core.type.TypeBody;
import org.snapscript.tree.StaticInvocationBuilder;

public class StaticFunctionBuilder implements MemberFunctionBuilder {
   
   private final Constraint constraint;
   private final Signature signature;
   private final Statement statement;
   private final String name;
   private final int modifiers;

   public StaticFunctionBuilder(Signature signature, Statement statement, Constraint constraint, String name, int modifiers) {
      this.constraint = constraint;
      this.signature = signature;
      this.modifiers = modifiers;
      this.statement = statement;
      this.name = name;
   }
   
   @Override
   public FunctionBody create(TypeBody body, Scope scope, Type type){
      Execution execution = new StaticBody(body, type); 
      InvocationBuilder builder = new StaticInvocationBuilder(signature, execution, statement, constraint);
      Invocation invocation = new StaticInvocation(builder, scope);
      Function function = new InvocationFunction(signature, invocation, type, constraint, name, modifiers);
      
      return new FunctionBody(builder, null, function);
   }
}