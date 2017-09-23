package org.snapscript.core.link;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ImportPathSource {
   
   private volatile DefaultImportReader reader;
   private volatile ImportPath path;
   
   public ImportPathSource(String file) {
      this.reader = new DefaultImportReader(file);
   }
   
   public ImportPath getPath() {
      if(path == null) {
         DefaultPath local = new DefaultPath();
         
         for(DefaultImport entry : reader) {
            String module = entry.getPackage();
            String name = entry.getAlias();
            Set<String> imports = entry.getImports();
            
            if(entry.isInclude()) {
               local.defaults.add(module);
            }
            for(String type : imports) {
               local.types.put(type, module);
            }
            local.aliases.put(name, module);
         }
         path = local;
      }
      return path;
   }
   
   private static class DefaultPath implements ImportPath {

      private final Map<String, String> aliases;
      private final Map<String, String> types;
      private final Set<String> defaults;
      
      public DefaultPath() {
         this.aliases = new LinkedHashMap<String, String>();
         this.types = new LinkedHashMap<String, String>();
         this.defaults = new LinkedHashSet<String>();
      }
      
      @Override
      public Map<String, String> getAliases() {
         return aliases;
      }

      @Override
      public Map<String, String> getTypes() {
         return types;
      }

      @Override
      public Set<String> getDefaults() {
         return defaults;
      }
   }
}