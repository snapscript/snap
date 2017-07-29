package org.snapscript.core.bridge;

import java.lang.reflect.Constructor;

import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.bind.FunctionResolver;
import org.snapscript.core.bind.ObjectFunctionMatcher;
import org.snapscript.core.stack.ThreadStack;

public class PlatformBridgeLoader {
   
   private final PlatformClassLoader loader;
   private final FunctionResolver resolver;
   
   public PlatformBridgeLoader(TypeExtractor extractor, ThreadStack stack) {
      this.resolver = new ObjectFunctionMatcher(extractor, stack);
      this.loader = new PlatformClassLoader();
   }

   public BridgeBuilder create(Type type) {
      if(type != null) {
         try {
            Constructor constructor = loader.loadConstructor();
            Object extender = constructor.newInstance(resolver, type);
            
            return (BridgeBuilder)extender;
         }catch(Exception e) {
            throw new IllegalStateException("Could not extend " + type, e);
         }
      }
      return null;
   }
}