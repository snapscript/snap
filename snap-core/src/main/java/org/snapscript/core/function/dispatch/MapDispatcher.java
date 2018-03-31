package org.snapscript.core.function.dispatch;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.Map;
import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.ProxyWrapper;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.find.FunctionCall;
import org.snapscript.core.function.find.FunctionFinder;

public class MapDispatcher implements FunctionDispatcher<Map> {
   
   private final FunctionFinder binder;
   private final ErrorHandler handler;
   private final String name;      
   
   public MapDispatcher(FunctionFinder binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Type map, Type... arguments) throws Exception {
      FunctionCall local = binder.bindInstance(scope, map, name, arguments);
      
      if(local != null) {
         return local.check();
      }
      return NONE;      
   }
   
   @Override
   public Value dispatch(Scope scope, Map map, Object... arguments) throws Exception {
      FunctionCall call = bind(scope, map, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, map, name, arguments);
      }
      return call.call();
   }
   
   private FunctionCall bind(Scope scope, Map map, Object... arguments) throws Exception {
      Module module = scope.getModule();
      FunctionCall local = binder.bindInstance(scope, map, name, arguments);
      
      if(local == null) {
         Object value = map.get(name);
         
         if(value != null) {
            Context context = module.getContext();
            ProxyWrapper wrapper = context.getWrapper();
            Object function = wrapper.fromProxy(value);
            Value reference = Value.getTransient(function);
            
            return binder.bindValue(reference, arguments);
         }
      }
      return local;
   }
}