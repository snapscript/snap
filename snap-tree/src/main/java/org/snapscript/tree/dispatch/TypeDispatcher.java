package org.snapscript.tree.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.tree.NameReference;

public class TypeDispatcher implements InvocationDispatcher<Type> {
   
   private final NameReference reference;
   
   public TypeDispatcher(NameReference reference) {
      this.reference = reference;
   }

   @Override
   public Value dispatch(Scope scope, Type type, Object... arguments) throws Exception {   
      Callable<Value> call = bind(scope, type, arguments);
      
      if(call == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         ErrorHandler handler = context.getHandler();
         String name = reference.getName(scope);
         
         handler.throwInternalException(scope, type, name, arguments);
      }
      return call.call();          
   } 
   
   private Callable<Value> bind(Scope scope, Type type, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();   
      String name = reference.getName(scope);
      Callable<Value> call = binder.bind(scope, type, name, arguments);
      
      if(call == null) {
         return binder.bind(scope, (Object)type, name, arguments);
      }
      return call;
   }
}