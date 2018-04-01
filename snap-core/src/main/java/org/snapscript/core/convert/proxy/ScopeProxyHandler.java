package org.snapscript.core.convert.proxy;

import java.lang.reflect.Method;

import org.snapscript.core.Context;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.function.search.FunctionCall;
import org.snapscript.core.function.search.FunctionSearcher;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;

public class ScopeProxyHandler implements ProxyHandler {
   
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
      FunctionSearcher binder = context.getSearcher();  
      Object[] convert = extractor.extract(arguments);
      FunctionCall call = binder.searchInstance(scope, scope, name, convert); // here arguments can be null!!!
      
      if(call == null) {
         throw new InternalStateException("Method '" + name + "' not found");
      }
      Value value = call.call();
      Object result = value.getValue();
      
      return result;
   }
   
   @Override
   public Scope extract() {
      return scope;
   }   
}