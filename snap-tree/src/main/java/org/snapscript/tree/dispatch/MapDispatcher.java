package org.snapscript.tree.dispatch;

import java.util.Map;
import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.ValueTypeExtractor;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.convert.ProxyWrapper;

public class MapDispatcher implements InvocationDispatcher {
   
   private final ValueTypeExtractor extractor;
   private final Object object;
   private final Scope scope;      
   
   public MapDispatcher(ValueTypeExtractor extractor, Scope scope, Object object) {
      this.extractor = extractor;
      this.object = object;
      this.scope = scope;
   }

   @Override
   public Value dispatch(String name, Object... arguments) throws Exception {
      Callable<Result> call = bind(name, arguments);
      
      if(call == null) {
         Type type = extractor.extract(scope, object);
         Module module = type.getModule();
         
         throw new InternalStateException("Method '" + name + "' not found for " + module + "." + type);
      }
      Result result = call.call();
      Object value = result.getValue();

      return ValueType.getTransient(value);
   }
   
   private Callable<Result> bind(String name, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      Callable<Result> local = binder.bind(scope, object, name, arguments);
      
      if(local == null) {
         Map map = (Map)object;
         Object object = map.get(name);
         
         if(object != null) {
            ProxyWrapper wrapper = context.getWrapper();
            Object function = wrapper.fromProxy(object);
            Value reference = ValueType.getTransient(function);
            
            return binder.bind(reference, arguments);
         }
      }
      return local;
   }
}