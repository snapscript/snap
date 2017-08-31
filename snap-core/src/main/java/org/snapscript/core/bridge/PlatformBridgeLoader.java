package org.snapscript.core.bridge;

import java.lang.reflect.Constructor;
import java.util.concurrent.Executor;

import org.snapscript.common.thread.ThreadPool;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.bind.FunctionResolver;

public class PlatformBridgeLoader {
   
   private final PlatformClassLoader loader;
   private final FunctionResolver resolver;
   private final BridgeBuilder builder;
   private final Executor executor;
   
   public PlatformBridgeLoader(TypeExtractor extractor) {
      this.resolver = new FunctionResolver(extractor);
      this.builder = new PartialBridgeBuilder();
      this.loader = new PlatformClassLoader();
      this.executor = new ThreadPool(1);
   }

   public BridgeBuilder create(Type type) {
      if(type != null) {
         try {
            Constructor constructor = loader.loadConstructor();
            Object extender = constructor.newInstance(resolver, executor, type);
            
            return (BridgeBuilder)extender;
         }catch(Exception e) {
            return builder; 
         }
      }
      return null;
   }
}