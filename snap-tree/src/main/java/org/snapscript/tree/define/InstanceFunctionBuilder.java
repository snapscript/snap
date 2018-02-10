package org.snapscript.tree.define;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.ModifierType;
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
import org.snapscript.tree.StatementInvocationBuilder;

public class InstanceFunctionBuilder implements MemberFunctionBuilder {
      
   private final Signature signature;
   private final Statement body;
   private final Type constraint;
   private final String name;
   private final int modifiers;

   public InstanceFunctionBuilder(Signature signature, Statement body, Type constraint, String name, int modifiers) {
      this.constraint = constraint;
      this.modifiers = modifiers;
      this.signature = signature;
      this.body = body;
      this.name = name;
   }
   
   @Override
   public FunctionHandle create(TypeFactory factory, Scope scope, Type type){
      InvocationBuilder builder = new StatementInvocationBuilder(signature, body, body, constraint);
      Invocation invocation = new InstanceInvocation(builder, name, body == null);
      Function function = new InvocationFunction(signature, invocation, type, constraint, name, modifiers);
      
      if(!ModifierType.isAbstract(modifiers)) {
         if(body == null) {
            throw new InternalStateException("Function '" + function + "' is not abstract");
         }
      }
      return new FunctionHandle(builder, function, body);
   }
}