package org.snapscript.core.type.extend;

import java.util.Collections;
import java.util.List;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.InternalException;
import org.snapscript.core.function.Function;
import org.snapscript.core.module.Module;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeLoader;

public class ExtensionRegistry {

   private final Cache<Class, Class> extensions;
   private final FunctionExtractor extractor;
   private final TypeLoader loader;
   
   public ExtensionRegistry(TypeLoader loader){
      this.extensions = new CopyOnWriteCache<Class, Class>();
      this.extractor = new FunctionExtractor(loader);
      this.loader = loader;
   }
   
   public void register(Class type, Class extension) {
      extensions.cache(type, extension);
   }
   
   public List<Function> extract(Class type) {
      Class extension = extensions.fetch(type);
      
      if(extension != null) {
         try {
            Object instance = extension.newInstance();
            Type match = loader.loadType(type);
            Module module = match.getModule();
            
            return extractor.extract(module, type, instance);
         } catch(Exception e) {
            throw new InternalException("Could not extend " + type, e);
         }
      }
      return Collections.emptyList();
   }
}