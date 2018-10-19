package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.Context;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.ArgumentList;

public class EnumConstructorBinder {

   private final ArgumentList arguments;
   
   public EnumConstructorBinder(ArgumentList arguments) {
      this.arguments = arguments;
   }
   
   public Value bind(Scope scope, Type type) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionResolver resolver = context.getResolver();
      
      if(arguments != null) {
         Object[] array = arguments.create(scope, type); // arguments have no left hand side
         return Value.getTransient(resolver.resolveStatic(scope, type, TYPE_CONSTRUCTOR, array).invoke(scope, null, array));
      }
      return Value.getTransient(resolver.resolveStatic(scope, type, TYPE_CONSTRUCTOR, type).invoke(scope, null, type));
   }
}