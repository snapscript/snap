package org.snapscript.core.convert.proxy;

import java.lang.reflect.Proxy;

import org.snapscript.core.Context;
import org.snapscript.core.ContextClassLoader;
import org.snapscript.core.convert.InterfaceCollector;
import org.snapscript.core.function.Function;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Any;

public class ProxyFactory {

   private final InterfaceCollector collector;
   private final ProxyWrapper wrapper;
   private final ClassLoader loader;
   private final Context context;
   
   public ProxyFactory(ProxyWrapper wrapper, Context context) {
      this.loader = new ContextClassLoader(Any.class);
      this.collector = new InterfaceCollector();
      this.wrapper = wrapper;
      this.context = context;
   }
   
   public Object create(Scope scope) {
      Class[] interfaces = collector.collect(scope);
      
      if(interfaces.length > 0) {
         ScopeProxyHandler handler = new ScopeProxyHandler(wrapper, context, scope);
         TraceProxyHandler tracer = new TraceProxyHandler(handler, context, scope);
         
         return Proxy.newProxyInstance(loader, interfaces, tracer);
      }
      return scope;
   }
   
   public Object create(Function function, Class... require) {
      Class[] interfaces = collector.filter(require);
      
      if(interfaces.length > 0) {
         FunctionProxyHandler handler = new FunctionProxyHandler(wrapper, context, function);

         return Proxy.newProxyInstance(loader, interfaces, handler);
      }
      return function;
   }
}