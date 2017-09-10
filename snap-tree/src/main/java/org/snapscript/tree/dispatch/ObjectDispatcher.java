package org.snapscript.tree.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Bug;
import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;

public class ObjectDispatcher implements InvocationDispatcher {
   
   private final Object object;
   private final Scope scope;      
   
   public ObjectDispatcher(Scope scope, Object object) {
      this.object = object;
      this.scope = scope;
   }

   @Bug("better descriptions needed including parameters")
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