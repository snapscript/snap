package org.snapscript.core.convert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.bind.FunctionBinder;

public class ScopeProxyHandler implements InvocationHandler {
   
   private final ProxyArgumentExtractor extractor;
   private final Context context;
   private final Scope scope;
   
   public ScopeProxyHandler(ProxyWrapper wrapper, Context context, Scope scope) {
      this.extractor = new ProxyArgumentExtractor(wrapper);
      this.context = context;
      this.scope = scope;
   }
   
   @Override
   public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
      String name = method.getName();
      FunctionBinder binder = context.getBinder();  
      Object[] convert = extractor.extract(arguments);
      Callable<Result> call = binder.bind(scope, scope, name, convert); // here arguments can be null!!!
      
      if(call == null) {
         throw new InternalStateException("Method '" + name + "' not found");
      }
      Result result = call.call();
      Object data = result.getValue();
      
      return data;   
   }
   
   public Scope extract() {
      return scope;
   }
   
}
