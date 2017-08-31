package org.snapscript.core.bridge;

import org.snapscript.core.Type;
import org.snapscript.core.TypeCache;
import org.snapscript.core.TypeExtractor;

public class PlatformBridgeProvider implements BridgeProvider {

   private final TypeCache<BridgeBuilder> cache;
   private final PlatformBridgeLoader builder;
   
   public PlatformBridgeProvider(TypeExtractor extractor) {
      this.builder = new PlatformBridgeLoader(extractor);
      this.cache = new TypeCache<BridgeBuilder>();
   }

   @Override
   public BridgeBuilder create(Type type) {
      if(type != null) {
         BridgeBuilder extender = cache.fetch(type);
         
        if(extender == null) {
            extender = builder.create(type);
            cache.cache(type, extender);
         }
         return extender;
      }
      return null;
   }
}