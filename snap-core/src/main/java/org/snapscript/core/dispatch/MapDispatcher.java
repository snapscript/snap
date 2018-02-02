package org.snapscript.core.dispatch;

import java.util.Map;
import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.convert.ProxyWrapper;
import org.snapscript.core.error.ErrorHandler;

public class MapDispatcher implements CallDispatcher<Map> {
   
   private final FunctionBinder binder;
   private final ErrorHandler handler;
   private final String name;      
   
   public MapDispatcher(FunctionBinder binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }

   @Override
   public Value dispatch(Scope scope, Map map, Object... arguments) throws Exception {
      Callable<Value> call = bind(scope, map, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, map, name, arguments);
      }
      return call.call();
   }
   
   private Callable<Value> bind(Scope scope, Map map, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Callable<Value> local = binder.bind(scope, map, name, arguments);
      
      if(local == null) {
         Object value = map.get(name);
         
         if(value != null) {
            Context context = module.getContext();
            ProxyWrapper wrapper = context.getWrapper();
            Object function = wrapper.fromProxy(value);
            Value reference = Value.getTransient(function);
            
            return binder.bind(reference, arguments);
         }
      }
      return local;
   }
}