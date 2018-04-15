package org.snapscript.core.module;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Context;
import org.snapscript.core.InternalArgumentException;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.type.NameBuilder;
import org.snapscript.core.type.extend.ModuleExtender;

public class ModuleRegistry {

   private final Map<String, Module> modules;
   private final List<Module> references;
   private final PathConverter converter;
   private final ModuleExtender extender;
   private final AtomicInteger counter;
   private final NameBuilder builder;
   private final Executor executor;
   private final Context context;
   private final int limit;
   
   public ModuleRegistry(Context context, Executor executor) {
      this(context, executor, 100000);
   }
   
   public ModuleRegistry(Context context, Executor executor, int limit) {
      this.modules = new ConcurrentHashMap<String, Module>();
      this.references = new CopyOnWriteArrayList<Module>();
      this.extender = new ModuleExtender(context);
      this.converter = new FilePathConverter();
      this.builder = new NameBuilder();
      this.counter = new AtomicInteger(1);
      this.executor = executor;
      this.context = context;
      this.limit = limit;
   }
   
   public synchronized List<Module> getModules() {
      return references;
   }

   public synchronized Module getModule(String name) {
      if (name == null) {
         throw new InternalArgumentException("Module name was null");
      }
      return modules.get(name);
   }

   public synchronized Module addModule(String name) {
      if (name == null) {
         throw new InternalArgumentException("Module name was null");
      }
      Path path = converter.createPath(name);
      Module current = modules.get(name);
      
      if(current == null) {
         return addModule(name, path);
      }
      return current;
   }
   
   public synchronized Module addModule(String name, Path path) {
      if (name == null) {
         throw new InternalArgumentException("Module name was null");
      }
      Module current = modules.get(name);

      if (current == null) {
         String local = builder.createLocalName(name);
         int order = counter.getAndIncrement();
         
         if(order > limit) {
            throw new InternalStateException("Module limit of " + limit + " exceeded");
         }
         Module module = new ContextModule(context, executor, path, name, local, order);

         modules.put(name, module);
         extender.extend(module); 
         references.add(module);
         
         return module;
      }
      return current;
   }
}