package org.snapscript.core.platform;

import org.snapscript.core.TypeExtractor;

public class CachePlatformProvider implements PlatformProvider {

   private PlatformBuilder loader;
   private Platform builder;
   
   public CachePlatformProvider(TypeExtractor extractor) {
      this.loader = new PlatformBuilder(extractor);
   }

   @Override
   public Platform create() {
      if(builder == null) {
         builder = loader.create();
      }
      return builder;
   }
}