package org.snapscript.core.generate;

import java.lang.reflect.Constructor;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Any;
import org.snapscript.core.Bug;
import org.snapscript.core.ContextClassLoader;
import org.snapscript.core.Type;
import org.snapscript.core.bind.FunctionResolver;

public class TypeExtenderLoader {
   
   private final AtomicReference<Constructor> reference;
   private final ClassLoader loader;
   
   public TypeExtenderLoader() {
      this.reference = new AtomicReference<Constructor>();
      this.loader = new ContextClassLoader(Any.class);
   }
   
   @Bug("exception description")
   public Constructor load(){
      Constructor constructor = reference.get();
      
      if(constructor == null) {
         try {
            Platform platform = Platform.resolvePlatform();
            Class value = loader.loadClass(platform.type);
            
            constructor = value.getDeclaredConstructor(FunctionResolver.class, Type.class);
         }catch(Exception e) {
            throw new IllegalStateException("Could not load extender", e);
         }
         reference.set(constructor);
      }
      return constructor;
   }
}