package org.snapscript.core.module;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Context;
import org.snapscript.core.ResourceManager;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.function.Function;
import org.snapscript.core.link.ImportManager;
import org.snapscript.core.property.Property;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.TypeLoader;
import org.snapscript.core.type.Type;

public class ContextModule implements Module {
   
   private final Cache<String, Module> modules;
   private final Cache<String, Type> types;
   private final List<Annotation> annotations;  
   private final List<Property> properties; 
   private final List<Function> functions; 
   private final List<Type> references;
   private final ImportManager manager;
   private final Context context;
   private final String prefix;
   private final Scope scope;
   private final Path path;
   private final Type type;
   private final int order;
   
   public ContextModule(Context context, Executor executor, Path path, String prefix, String local) {
      this(context, executor, path, prefix, local, 0);
   }
   
   public ContextModule(Context context, Executor executor, Path path, String prefix, String local, int order) {
      this.manager = new ImportManager(this, executor, path, prefix, local);
      this.annotations = new CopyOnWriteArrayList<Annotation>();
      this.functions = new CopyOnWriteArrayList<Function>();
      this.properties = new CopyOnWriteArrayList<Property>();
      this.references = new CopyOnWriteArrayList<Type>();
      this.modules = new CopyOnWriteCache<String, Module>();
      this.types = new CopyOnWriteCache<String, Type>(); 
      this.type = new ModuleType(this);
      this.scope = new ModuleScope(this);
      this.context = context;
      this.prefix = prefix;
      this.order = order;
      this.path = path;
   }

   @Override
   public Context getContext() {
      return context;
   }
   
   @Override
   public ImportManager getManager() {
      return manager;
   }
   
   @Override
   public List<Annotation> getAnnotations() {
      return annotations;
   }

   @Override
   public List<Function> getFunctions() {
      return functions;
   }
   
   @Override
   public List<Property> getProperties() {
      return properties;
   }
   
   @Override
   public List<Type> getTypes() {
      return references;
   }
   
   @Override
   public Type addType(String name, Category category) {
      Type type = types.fetch(name); 
      
      if(type != null) {
         throw new ModuleException("Type '" + prefix + "." + name + "' already defined");
      }
      try {
         TypeLoader loader = context.getLoader();
         
         if(loader != null) {
            type = loader.defineType(prefix, name, category);
         }
         if(type != null) {
            types.cache(name, type);
            references.add(type);
         }
         return type;
      } catch(Exception e){
         throw new ModuleException("Could not define '" + prefix + "." + name + "'", e);
      }
   }
   
   @Override
   public Module getModule(String name) {
      try {
         Module module = modules.fetch(name);
         
         if(module == null) {
            if(!types.contains(name)) { // don't resolve if its a type
               module = manager.getModule(name); // import tetris.game.*
               
               if(module != null) {
                  modules.cache(name, module);
               }
            }
         }
         return module;
      } catch(Exception e){
         throw new ModuleException("Could not find '" + name + "' in '" + prefix + "'", e);
      }
   }
   
   @Override
   public Type getType(String name) {
      try {
         Type type = types.fetch(name);

         if(type == null) {
            if(!modules.contains(name)) {// don't resolve if its a module
               type = manager.getType(name);
            
               if(type != null) {
                  types.cache(name, type);
                  references.add(type);
               }
            }
         }
         return type;
      } catch(Exception e){
         throw new ModuleException("Could not find '" + name + "' in '" + prefix + "'", e);
      }
   }
   
   @Override
   public Type getType(Class type) {
      try {
         TypeLoader loader = context.getLoader();
         
         if(loader != null) {
            return loader.loadType(type);
         }
         return null;
      } catch(Exception e){
         throw new ModuleException("Could not load " + type, e);
      }
   }   
   
   @Override
   public InputStream getResource(String path) {
      try {
         ResourceManager manager = context.getManager();

         if(manager != null) {
            return manager.getInputStream(path);
         }
         return null;
      } catch(Exception e){
         throw new ModuleException("Could not load file '" + path + "'", e);
      }
   }

   @Override
   public Scope getScope() {
      return scope;
   }
   
   @Override
   public Type getType() {
      return type;
   }
   
   @Override
   public String getName() {
      return prefix;
   }
   
   @Override
   public Path getPath() {
      return path;
   }
   
   @Override
   public int getOrder() {
      return order;
   }

   @Override
   public String toString() {
      return prefix;
   }
}