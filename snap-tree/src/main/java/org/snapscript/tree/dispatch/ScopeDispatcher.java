package org.snapscript.tree.dispatch;

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

public class ScopeDispatcher implements InvocationDispatcher {
   
   private final ValueTypeExtractor extractor;
   private final Scope object;
   private final Scope scope;      
   
   public ScopeDispatcher(ValueTypeExtractor extractor, Scope scope, Object object) {
      this.object = (Scope)object;
      this.extractor = extractor;
      this.scope = scope;
   }

   @Override
   public Value dispatch(String name, Object... arguments) throws Exception {
      Callable<Result> match = bind(name, arguments);
      
      if(match == null) {
         Type type = extractor.extract(scope, object);
         Module module = type.getModule();
         
         throw new InternalStateException("Method '" + name + "' not found for " + module + "." + type);   
      }
      Result result = match.call();
      Object data = result.getValue();
      
      return ValueType.getTransient(data);           
   }
   
   private Callable<Result> bind(String name, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      Callable<Result> local = binder.bind(scope, object, name, arguments);
      
      if(local == null) {
         Callable<Result> external = binder.bind(scope, module, name, arguments); // maybe closure should be first
         
         if(external != null) {
            return external;
         }
         Callable<Result> closure = binder.bind(object, name, arguments); // closure
         
         if(closure != null) {
            return closure;
         }
      }
      return local;  
   }
}