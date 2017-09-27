package org.snapscript.tree.dispatch;

import java.util.Map;
import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.convert.ProxyWrapper;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.tree.NameReference;

public class MapDispatcher implements InvocationDispatcher<Map> {
   
   private final NameReference reference;      
   
   public MapDispatcher(NameReference reference) {
      this.reference = reference;
   }

   @Override
   public Value dispatch(Scope scope, Map map, Object... arguments) throws Exception {
      Callable<Value> call = bind(scope, map, arguments);
      
      if(call == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         ErrorHandler handler = context.getHandler();
         String name = reference.getName(scope);

         handler.throwInternalException(scope, map, name, arguments);
      }
      return call.call();
   }
   
   private Callable<Value> bind(Scope scope, Map map, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      String name = reference.getName(scope);
      Callable<Value> local = binder.bind(scope, map, name, arguments);
      
      if(local == null) {
         Object value = map.get(name);
         
         if(value != null) {
            ProxyWrapper wrapper = context.getWrapper();
            Object function = wrapper.fromProxy(value);
            Value reference = Value.getTransient(function);
            
            return binder.bind(reference, arguments);
         }
      }
      return local;
   }
}