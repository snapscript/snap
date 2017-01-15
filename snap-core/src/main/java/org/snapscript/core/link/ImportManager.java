package org.snapscript.core.link;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.Path;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;

public class ImportManager {

   private final Map<String, String> aliases;
   private final Set<String> imports;
   private final ImportMatcher matcher;
   private final Context context;
   private final String from;
   
   public ImportManager(Context context, Path path, String from) {
      this.aliases = new ConcurrentHashMap<String, String>();
      this.imports = new CopyOnWriteArraySet<String>();
      this.matcher = new ImportMatcher(context, path, from);
      this.context = context;
      this.from = from;
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
         Module module = registry.getModule(name);
         
         if(module == null) {
            String alias = aliases.get(name);
            
            if(alias != null) {
               module = registry.getModule(alias);
            }
         }
         if(module == null) {
            module = matcher.importModule(name, imports);
         }
         return module;
      } catch(Exception e){
         throw new InternalStateException("Could not find '" + name + "' in '" + from + "'", e);
      }
   }

   public Type getType(String name) {
      try {
         TypeLoader loader = context.getLoader();
         Type type = loader.resolveType(from, name);

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
               type = matcher.importType(name, imports);
            }
         }
         return type;
      } catch(Exception e){
         throw new InternalStateException("Could not find '" + name + "' in '" + from + "'", e);
      }
   }

}
