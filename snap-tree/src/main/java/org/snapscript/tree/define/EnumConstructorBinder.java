package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.Context;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.type.Type;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.construct.ConstructArgumentList;

public class EnumConstructorBinder {

   private final ConstructArgumentList arguments;
   
   public EnumConstructorBinder(ArgumentList arguments) {
      this.arguments = new ConstructArgumentList(arguments);
   }
   
   public Instance bind(Scope scope, Type type) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      FunctionResolver resolver = context.getResolver();
      Object[] array = arguments.create(scope, type);
      FunctionCall call = resolver.resolveStatic(scope, type, TYPE_CONSTRUCTOR, array);
      
      if(call == null) {
         handler.failRuntimeInvocation(scope, type, TYPE_CONSTRUCTOR, array);
      }
      return (Instance)call.invoke(scope, null, array);
   }
}