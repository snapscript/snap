package org.snapscript.core.function.dispatch;

import static org.snapscript.core.constraint.Constraint.NONE;
import static org.snapscript.core.error.Reason.INVOKE;

import java.util.Map;

import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.error.Reason;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Type;

public class MapDispatcher implements FunctionDispatcher<Map> {
   
   private final FunctionResolver binder;
   private final ErrorHandler handler;
   private final String name;      
   
   public MapDispatcher(FunctionResolver binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Type map, Type... arguments) throws Exception {
      FunctionCall local = binder.resolveInstance(scope, map, name, arguments);
      
      if(local != null) {
         return local.check();
      }
      return NONE;      
   }
   
   @Override
   public Value evaluate(Scope scope, Map map, Object... arguments) throws Exception {
      FunctionCall call = bind(scope, map, arguments);
      
      if(call == null) {
         handler.handleRuntimeError(INVOKE, scope, map, name, arguments);
      }
      return call.call();
   }
   
   private FunctionCall bind(Scope scope, Map map, Object... arguments) throws Exception {
      Module module = scope.getModule();
      FunctionCall local = binder.resolveInstance(scope, map, name, arguments);
      
      if(local == null) {
         Object value = map.get(name);
         
         if(value != null) {
            Context context = module.getContext();
            ProxyWrapper wrapper = context.getWrapper();
            Object function = wrapper.fromProxy(value);
            Value reference = Value.getTransient(function);
            
            return binder.resolveValue(reference, arguments);
         }
      }
      return local;
   }
}