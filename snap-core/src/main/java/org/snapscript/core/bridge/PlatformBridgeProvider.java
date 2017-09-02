package org.snapscript.core.bridge;

import org.snapscript.core.TypeExtractor;

public class PlatformBridgeProvider implements BridgeProvider {

   private PlatformBridgeLoader loader;
   private BridgeBuilder builder;
   
   public PlatformBridgeProvider(TypeExtractor extractor) {
      this.loader = new PlatformBridgeLoader(extractor);
   }

   @Override
   public BridgeBuilder create() {
      if(builder == null) {
         builder = loader.create();
      }
      return builder;
   }
}