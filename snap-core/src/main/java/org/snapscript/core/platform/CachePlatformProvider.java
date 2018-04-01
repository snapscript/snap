package org.snapscript.core.platform;

import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.TypeExtractor;

public class CachePlatformProvider implements PlatformProvider {

   private PlatformBuilder loader;
   private Platform builder;
   
   public CachePlatformProvider(TypeExtractor extractor, ProxyWrapper wrapper, ThreadStack stack) {
      this.loader = new PlatformBuilder(extractor, wrapper, stack);
   }

   @Override
   public Platform create() {
      if(builder == null) {
         builder = loader.create();
      }
      return builder;
   }
}