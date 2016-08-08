package org.snapscript.tree.dispatch;

import java.util.List;
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
import org.snapscript.tree.collection.ArrayBuilder;

public class ArrayDispatcher implements InvocationDispatcher {
   
   private final ValueTypeExtractor extractor;
   private final ArrayBuilder builder;
   private final Object object;
   private final Scope scope;      
   
   public ArrayDispatcher(ValueTypeExtractor extractor, Scope scope, Object object) {
      this.builder = new ArrayBuilder();
      this.extractor = extractor;
      this.object = object;
      this.scope = scope;
   }

   @Override
   public Value dispatch(String name, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      List list = builder.convert(object);
      Callable<Result> call = binder.bind(scope, list, name, arguments);
      
      if(call == null) {
         Type type = extractor.extract(scope, object);
         
         throw new InternalStateException("Method '" + name + "' not found for " + module + "." + type + "[]");
      }
      Result result = call.call();
      Object value = result.getValue();

      return ValueType.getTransient(value);
   }
}