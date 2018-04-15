package org.snapscript.core.link;

import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import org.snapscript.core.ApplicationValidator;
import org.snapscript.core.Context;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.ModuleRegistry;
import org.snapscript.core.module.Path;
import org.snapscript.core.type.NameBuilder;
import org.snapscript.core.type.Type;

public class ImportMatcher {

   private final ImportTaskResolver resolver;
   private final NameBuilder builder;
   private final Module parent;
   private final String from;
   
   public ImportMatcher(Module parent, Executor executor, Path path, String from) {
      this.resolver = new ImportTaskResolver(parent, executor, path);
      this.builder = new NameBuilder();
      this.parent = parent;
      this.from = from;
   }

   public Type importType(Set<String> prefixes, String name) throws Exception {
      Context context = parent.getContext();
      ModuleRegistry registry = context.getRegistry();

      for(String prefix : prefixes) {
         Module match = registry.getModule(prefix);
         
         if(match != parent && match != null) {
            Type type = match.getType(name); // get imports from the outer module if it exists

            if(type != null) {
               return type;
            }
         }
      }
      for(String prefix : prefixes) {
         Type type = importType(prefix, name);
         
         if(type != null) {
            return type;
         }
      }
      String type = builder.createFullName(from, name);
      Module module = registry.getModule(type);
      
      if(module == null){ 
         return importType(from, name); // import from current package
      }
      return null;
   }
   
   private Type importType(String prefix, String name) throws Exception {
      String qualifier = builder.createFullName(prefix, name);
      Future<Type> task = resolver.importType(qualifier);
      
      if(task != null) {
         Context context = parent.getContext();
         ApplicationValidator validator = context.getValidator();
         Type type = task.get();
         
         if(type != null) {
            validator.validate(type);
         }
         return type;
      }
      return null;
   }
   
   public Module importModule(Set<String> prefixes, String name) throws Exception {
      Context context = parent.getContext();
      ModuleRegistry registry = context.getRegistry();
      
      for(String prefix : prefixes) {
         String inner = builder.createFullName(prefix, name);
         Module match = registry.getModule(inner); // get imports from the outer module if it exists
         
         if(match != null) {
            return match;
         }
      }
      for(String prefix : prefixes) {
         Module module = importModule(prefix, name);
         
         if(module != null) {
            return module;
         }
      }
      return importModule(from, name); // import from current package
   }
   
   private Module importModule(String prefix, String name) throws Exception {
      String qualifier = builder.createFullName(prefix, name);
      Future<Module> task = resolver.importModule(qualifier);
      
      if(task != null) {
         Context context = parent.getContext();
         ApplicationValidator validator = context.getValidator();
         Module module = task.get();
         
         if(module != null) {
            validator.validate(module);
         }
         return module;
      }
      return null;
   }
}