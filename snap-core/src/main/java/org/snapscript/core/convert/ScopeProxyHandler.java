package org.snapscript.core.convert;

import java.lang.reflect.Method;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.function.find.FunctionCall;
import org.snapscript.core.function.find.FunctionFinder;

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
      FunctionFinder binder = context.getFinder();  
      Object[] convert = extractor.extract(arguments);
      FunctionCall call = binder.bindInstance(scope, scope, name, convert); // here arguments can be null!!!
      
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