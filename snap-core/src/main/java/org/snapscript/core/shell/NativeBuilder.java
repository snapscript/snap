package org.snapscript.core.shell;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Any;
import org.snapscript.core.ContextClassLoader;

public class NativeBuilder implements ShellBuilder {
   
   private static final String CLASS = "sun.misc.Unsafe";
   private static final String FIELD = "theUnsafe";
   private static final String METHOD = "allocateInstance";
   
   private final AtomicReference<ObjectFactory> reference;
   private final AtomicBoolean failure;
   private final ClassLoader loader;
   
   public NativeBuilder() {
      this.reference = new AtomicReference<ObjectFactory>();
      this.loader = new ContextClassLoader(Any.class);
      this.failure = new AtomicBoolean();
   }
   
   @Override
   public Object create(Class type) {
      try {
         if(!failure.get()) {
            ObjectFactory factory = reference.get();
            
            if(factory == null) {
               factory = createFactory();
            }
            return factory.invoke(type);
         }
      } catch(Throwable e) {
         failure.set(true);
      }
      return null;
   }
   
   private ObjectFactory createFactory() throws Exception {
      Class type = loader.loadClass(CLASS);
      ObjectFactory factory = createFactory(type, FIELD, METHOD);

      if (factory != null) {
         reference.set(factory);
      }
      return factory;
   }

   private ObjectFactory createFactory(Class type, String field, String method) throws Exception {
      Object value = createInstance(type, field);
      Method accessor = type.getDeclaredMethod(method, Class.class);

      if (!accessor.isAccessible()) {
         accessor.setAccessible(true);
      }
      return new ObjectFactory(value, accessor);
   }
   
   private Object createInstance(Class type, String field) throws Exception {
      Field accessor = type.getDeclaredField(field);

      if (!accessor.isAccessible()) {
         accessor.setAccessible(true);
      }
      return accessor.get(null);
   }

   private static class ObjectFactory {

      private final Object factory;
      private final Method method;

      public ObjectFactory(Object factory, Method method) {
         this.factory = factory;
         this.method = method;
      }

      public Object invoke(Class type) throws Exception {
         return method.invoke(factory, type);
      }
   }
}
