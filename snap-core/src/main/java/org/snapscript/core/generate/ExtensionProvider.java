package org.snapscript.core.generate;

import org.snapscript.core.Type;
import org.snapscript.core.TypeCache;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.stack.ThreadStack;

public class ExtensionProvider {
   
   private final TypeCache<TypeExtender> cache;
   private final TypeExtenderBuilder builder;
   
   public ExtensionProvider(TypeExtractor extractor, ThreadStack stack) {
      this.builder = new TypeExtenderBuilder(extractor, stack);
      this.cache = new TypeCache<TypeExtender>();
   }

   public TypeExtender create(Type type) {
      if(type != null) {
         TypeExtender extender = cache.fetch(type);
         
        if(extender == null) {
            extender = builder.create(type);
            cache.cache(type, extender);
         }
         return extender;
      }
      return null;
   }
}
