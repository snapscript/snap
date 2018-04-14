package org.snapscript.core.link;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;

import org.snapscript.core.Context;
import org.snapscript.core.Execution;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Statement;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.ModuleRegistry;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeLoader;

public class ImportTaskBuilder {

   private final Module parent;
   private final Set imports;
   private final Path from;
   
   public ImportTaskBuilder(Module parent, Path from) {
      this.imports = new CopyOnWriteArraySet<Path>();
      this.parent = parent;
      this.from = from;
   }
   
   public Callable<Type> createType(String name, Path path) throws Exception{
      try {
         Context context = parent.getContext();
         TypeLoader loader = context.getLoader();
         Package module = loader.importType(name);
         
         return new TypeImport(loader, module, path, name); // import exceptions will propagate
      } catch(Exception e) {
         return null;
      }
   }
   
   public Callable<Module> createModule(String name, Path path) throws Exception{
      try {
         Context context = parent.getContext();
         TypeLoader loader = context.getLoader();
         ModuleRegistry registry = context.getRegistry();
         Package module = loader.importType(name);
         
         return new ModuleImport(registry, module, path, name); // import exceptions will propagate
      } catch(Exception e) {
         return null;
      }
   }
   
   private class TypeImport implements Callable<Type> {
      
      private final TypeLoader loader;
      private final Package module;
      private final String name;
      private final Path path;
      
      public TypeImport(TypeLoader loader, Package module, Path path, String name){
         this.loader = loader;
         this.module = module;
         this.name = name;
         this.path = path;
      }

      @Override
      public Type call() {
         try {
            if(!imports.contains(path)) {
               Scope scope = parent.getScope();
               PackageDefinition definition = module.create(scope); 
               Statement statement = definition.define(scope, from);
               Execution execution = statement.compile(scope, null);
               
               execution.execute(scope); 
               imports.add(path);
            }
         } catch(Exception e) {
            throw new InternalStateException("Could not import '" + path+"'", e);
         }
         return loader.resolveType(name);
      }
   }
   
   private class ModuleImport implements Callable<Module> {
      
      private final ModuleRegistry registry;
      private final Package module;
      private final String name;
      private final Path path;
      
      public ModuleImport(ModuleRegistry registry, Package module, Path path, String name){
         this.registry = registry;
         this.module = module;
         this.name = name;
         this.path = path;
      }

      @Override
      public Module call() {
         try {
            if(!imports.contains(path)) {
               Scope scope = parent.getScope();
               PackageDefinition definition = module.create(scope);
               Statement statement = definition.define(scope, from);
               Execution execution = statement.compile(scope, null);
               
               execution.execute(scope); 
               imports.add(path);
            }
         } catch(Exception e) {
            throw new InternalStateException("Could not import '" + path+"'", e);
         }
         return registry.getModule(name);
      }
   }
}