package org.snapscript.core.platform;

import java.lang.reflect.Constructor;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.TypeExtractor;
import org.snapscript.core.bind.FunctionResolver;

public class PlatformBuilder {
   
   private final AtomicReference<Platform> reference;
   private final PlatformClassLoader loader;
   private final FunctionResolver resolver;
   private final Platform partial;
   
   public PlatformBuilder(TypeExtractor extractor) {
      this.resolver = new FunctionResolver(extractor);
      this.loader = new PlatformClassLoader();
      this.partial = new PartialPlatform();
      this.reference = new AtomicReference<Platform>();
   }

   public synchronized Platform create() {
      Platform platform = reference.get(); // make sure only one created
      
      if(platform == null) {
         try {
            Constructor constructor = loader.loadConstructor();
            Object instance = constructor.newInstance(resolver);
            
            reference.set((Platform)instance);
         }catch(Exception e) {
            reference.set(partial); 
         }
      }
      return reference.get();
   }
}