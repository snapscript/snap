package org.snapscript.core.link;

import org.snapscript.core.Context;
import org.snapscript.core.Entity;
import org.snapscript.core.NameFormatter;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.ModuleRegistry;
import org.snapscript.core.type.TypeLoader;

public class ImportEntityResolver {
   
   private final NameFormatter formatter;
   private final Module parent;
   
   public ImportEntityResolver(Module parent){
      this.formatter = new NameFormatter();
      this.parent = parent;
   }
   
   public Entity resolveEntity(String type) {
      Context context = parent.getContext();
      TypeLoader loader = context.getLoader();
      ModuleRegistry registry = context.getRegistry();
      Module match = registry.getModule(type);
      
      if(match == null) {
         return loader.loadType(type);
      }
      return match;
   }
   
   public Entity resolveEntity(String module, String name) {
      String type = formatter.formatFullName(module, name);
      Context context = parent.getContext();
      TypeLoader loader = context.getLoader();
      ModuleRegistry registry = context.getRegistry();
      Module match = registry.getModule(type);
      
      if(match == null) {
         return loader.loadType(type);
      }
      return match;
   }
}