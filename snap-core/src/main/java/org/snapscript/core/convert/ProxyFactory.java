package org.snapscript.core.convert;

import java.lang.reflect.Proxy;

import org.snapscript.core.Any;
import org.snapscript.core.Context;
import org.snapscript.core.Scope;
import org.snapscript.core.function.Function;

public class ProxyFactory {

   private InterfaceCollector collector;
   private ProxyWrapper wrapper;
   private ClassLoader loader;
   private Context context;
   
   public ProxyFactory(ProxyWrapper wrapper, Context context) {
      this.collector = new InterfaceCollector();
      this.wrapper = wrapper;
      this.context = context;
   }
   
   public Object create(Scope scope, Class... require) {
      Class[] interfaces = collector.collect(scope);
      
      if(interfaces.length > 0) {
         ScopeProxyHandler handler = new ScopeProxyHandler(wrapper, context, scope);
         
         if(loader == null) {
            loader = Any.class.getClassLoader();
         }
         return Proxy.newProxyInstance(loader, interfaces, handler);
      }
      return scope;
   }
   
   public Object create(Function function, Class... require) {
      Class[] interfaces = collector.filter(require);
      
      if(interfaces.length > 0) {
         FunctionProxyHandler handler = new FunctionProxyHandler(wrapper, context, function);
         
         if(loader == null) {
            loader = Any.class.getClassLoader();
         }
         return Proxy.newProxyInstance(loader, interfaces, handler);
      }
      return function;
   }
}
