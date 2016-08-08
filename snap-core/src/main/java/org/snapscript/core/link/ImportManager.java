package org.snapscript.core.link;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;

public class ImportManager {

   private final Map<String, String> aliases;
   private final Set<String> imports;
   private final Context context;
   private final String prefix;
   
   public ImportManager(Context context, String prefix) {
      this.aliases = new ConcurrentHashMap<String, String>();
      this.imports = new CopyOnWriteArraySet<String>();
      this.context = context;
      this.prefix = prefix;
   }
   
   public void addImport(String prefix) {
      imports.add(prefix);
   }
   
   public void addImport(String type, String alias) {
      aliases.put(alias, type);
   }
   
   public void addImports(Module module) {
      ImportManager manager = module.getManager();

      if(manager != null) {
         manager.aliases.putAll(aliases);
         manager.imports.addAll(imports);
      }
   }
   
   public Module getModule(String name) {
      try {
         ModuleRegistry registry = context.getRegistry();
         String alias = aliases.get(name);
         
         if(alias != null) {
            Module module = registry.getModule(alias);
            
            if(module != null) {
               return module;
            }
         }
         for(String prefix : imports) {
            Module module = registry.getModule(prefix + "." + name); // get imports from the outer module if it exists
            
            if(module != null) {
               return module;
            }
         }
         return null;
      } catch(Exception e){
         throw new InternalStateException("Could not find '" + name + "' in '" + prefix + "'", e);
      }
   }

   public Type getType(String name) {
      try {
         TypeLoader loader = context.getLoader();
         Type type = loader.resolveType(prefix, name);

         if(type == null) {
            String alias = aliases.get(name); // fully qualified "tetris.game.Block"
            
            if(alias != null) {
               type = loader.resolveType(alias);
               
               if(type != null) {
                  return type;
               }
            }
            for(String module : imports) {
               type = loader.resolveType(module, name); // this is "tetris.game.*"
               
               if(type != null) {
                  return type;
               }
            }
            if(type == null) {
               type = loader.resolveType(null, name); // null is "java.*"
            }
            if(type == null) {
               ModuleRegistry registry = context.getRegistry();
               
               for(String prefix : imports) {
                  Module module = registry.getModule(prefix);
                  
                  if(module != null) {
                     type = module.getType(name); // get imports from the outer module if it exists

                     if(type != null) {
                        return type;
                     }
                  }
               }
            }
         }
         return type;
      } catch(Exception e){
         throw new InternalStateException("Could not find '" + name + "' in '" + prefix + "'", e);
      }
   }

}
