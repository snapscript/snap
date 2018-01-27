package org.snapscript.core.shell;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.snapscript.core.Any;
import org.snapscript.core.ContextClassLoader;

public class InterfaceBuilder implements ShellBuilder {

   private final InvocationHandler handler;
   private final ClassLoader loader;
   
   public InterfaceBuilder() {
      this.loader = new ContextClassLoader(Any.class);
      this.handler = new EmptyHandler();
   }
   
   @Override
   public Object create(Class type) {
      try {
         Class[] types = new Class[]{type};
         
         if(Map.class.isAssignableFrom(type)) {
            return Collections.emptyMap();
         }
         if(Set.class.isAssignableFrom(type)) {
            return Collections.emptySet();
         }
         if(List.class.isAssignableFrom(type)) {
            return Collections.emptyList();
         }
         if(type.isInterface()) {
            return Proxy.newProxyInstance(loader, types, handler);
         }
      } catch(Exception e) {
         return null;
      }
      return null;
   }
   
   private static class EmptyHandler implements InvocationHandler {

      @Override
      public Object invoke(Object proxy, Method method, Object[] list) {
         return null;
      }
      
   }
}
