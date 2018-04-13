package org.snapscript.tree.define;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.ModifierType;
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
import org.snapscript.tree.StatementInvocationBuilder;

public class InstanceFunctionBuilder implements MemberFunctionBuilder {
      
   private final Constraint constraint;
   private final Signature signature;
   private final Statement statement;
   private final String name;
   private final int modifiers;

   public InstanceFunctionBuilder(Signature signature, Statement statement, Constraint constraint, String name, int modifiers) {
      this.constraint = constraint;
      this.modifiers = modifiers;
      this.signature = signature;
      this.statement = statement;
      this.name = name;
   }
   
   @Override
   public FunctionBody create(TypeBody body, Scope scope, Type type){
      InvocationBuilder builder = new StatementInvocationBuilder(signature, statement, constraint);
      Invocation invocation = new InstanceInvocation(builder, name, statement == null);
      Function function = new InvocationFunction(signature, invocation, type, constraint, name, modifiers);
      
      if(!ModifierType.isAbstract(modifiers)) {
         if(statement == null) {
            throw new InternalStateException("Function '" + function + "' is not abstract");
         }
      }
      return new FunctionBody(builder, null, function);
   }
}