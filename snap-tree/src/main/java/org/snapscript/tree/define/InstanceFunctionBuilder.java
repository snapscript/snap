package org.snapscript.tree.define;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;

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
   public Function create(Scope scope, Initializer initializer, Type type){
      Invocation invocation = new InstanceInvocation(signature, body, constraint);
      Function function = new InvocationFunction(signature, invocation, type, constraint, name, modifiers);
      
      if(!ModifierType.isAbstract(modifiers)) {
         if(body == null) {
            throw new InternalStateException("Function '" + function + "' is not abstract");
         }
      }
      return function;
   }
}
