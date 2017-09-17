package org.snapscript.core.platform;

import org.snapscript.core.TypeExtractor;
import org.snapscript.core.stack.ThreadStack;

public class CachePlatformProvider implements PlatformProvider {

   private PlatformBuilder loader;
   private Platform builder;
   
   public CachePlatformProvider(TypeExtractor extractor, ThreadStack stack) {
      this.loader = new PlatformBuilder(extractor, stack);
   }

   @Override
   public Platform create() {
      if(builder == null) {
         builder = loader.create();
      }
      return builder;
   }
}