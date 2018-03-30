package org.snapscript.tree.define;

import org.snapscript.core.Execution;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionHandle;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.StaticInvocationBuilder;

public class StaticFunctionBuilder implements MemberFunctionBuilder {
   
   private final Constraint constraint;
   private final Signature signature;
   private final Statement body;
   private final String name;
   private final int modifiers;

   public StaticFunctionBuilder(Signature signature, Statement body, Constraint constraint, String name, int modifiers) {
      this.constraint = constraint;
      this.signature = signature;
      this.modifiers = modifiers;
      this.body = body;
      this.name = name;
   }
   
   @Override
   public FunctionHandle create(TypeFactory factory, Scope scope, Type type){
      Execution execution = new StaticBody(factory, type); 
      InvocationBuilder builder = new StaticInvocationBuilder(signature, execution, body, constraint);
      Invocation invocation = new StaticInvocation(builder, scope);
      Function function = new InvocationFunction(signature, invocation, type, constraint, name, modifiers);
      
      return new FunctionHandle(builder, null, function);
   }
}