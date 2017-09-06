package org.snapscript.tree.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.convert.Delegate;

public class DelegateDispatcher implements InvocationDispatcher {
   
   private final Delegate object;
   private final Scope scope;      
   
   public DelegateDispatcher(Scope scope, Object object) {
      this.object = (Delegate)object;
      this.scope = scope;
   }

   @Override
   public Value dispatch(String name, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      Callable<Value> call = binder.bind(scope, object, name, arguments);
      
      if(call == null) {
         TypeExtractor extractor = context.getExtractor();
         Type type = extractor.getType(object);
         
         throw new InternalStateException("Method '" + name + "' not found for '" + type + "'");
      }
      return call.call();
   }
}