package org.snapscript.core.link;

import java.util.Set;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.NameBuilder;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.TypeNameBuilder;

public class ImportMatcher {

   private final ImportProcessor processor;
   private final NameBuilder builder;
   private final Context context;
   private final String from;
   
   public ImportMatcher(Context context, Path path, String from) {
      this.processor = new ImportProcessor(context, path);
      this.builder = new TypeNameBuilder();
      this.context = context;
      this.from = from;
   }

   public Type importType(String name, Set<String> imports) throws Exception {
      TypeLoader loader = context.getLoader();
      ModuleRegistry registry = context.getRegistry();
      Module module = registry.getModule(from);
      Scope scope = module.getScope();

      for(String prefix : imports) {
         if(!prefix.equals(from)) { // avoid recursion
            Module match = registry.getModule(prefix);
            
            if(match != null) {
               Type type = match.getType(name); // get imports from the outer module if it exists

               if(type != null) {
                  return type;
               }
            }
         }
      }
      for(String prefix : imports) {
         String type = builder.createTopName(prefix, name);
         
         if(processor.importType(scope, type)) {
            return loader.resolveType(prefix, name);
         }
      }
      return null;
   }
   
   public Module importModule(String name, Set<String> imports) throws Exception {
      ModuleRegistry registry = context.getRegistry();
      Module module = registry.getModule(from);
      Scope scope = module.getScope();
      
      for(String prefix : imports) {
         String inner = builder.createFullName(prefix, name);
         
         if(!inner.equals(from)) { // avoid recursion
            Module match = registry.getModule(inner); // get imports from the outer module if it exists
            
            if(match != null) {
               return match;
            }
         }
      }
      for(String prefix : imports) {
         String type = builder.createTopName(prefix, name);
         
         if(processor.importType(scope, type)) {
            return registry.getModule(type);
         }
      }
      return null;
   }
}
