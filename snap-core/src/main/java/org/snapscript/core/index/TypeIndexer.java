package org.snapscript.core.index;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.Type;
import org.snapscript.core.extend.ClassExtender;
import org.snapscript.core.link.ImportScanner;

public class TypeIndexer {

   private final Map<Object, Type> types;
   private final TypeNameBuilder builder;
   private final ModuleRegistry registry;
   private final ImportScanner scanner;
   private final ClassIndexer indexer;
   private final AtomicInteger counter;
   private final int limit;
   
   public TypeIndexer(ModuleRegistry registry, ImportScanner scanner, ClassExtender extender) {
      this(registry, scanner, extender, 100000);
   }
   
   public TypeIndexer(ModuleRegistry registry, ImportScanner scanner, ClassExtender extender, int limit) {
      this.indexer = new ClassIndexer(this, registry, scanner, extender);
      this.types = new LinkedHashMap<Object, Type>();
      this.builder = new TypeNameBuilder();
      this.counter = new AtomicInteger(1); // consider function types which own 0
      this.registry = registry;
      this.scanner = scanner;
      this.limit = limit;
   }
   
   public synchronized Type loadType(String type) {
      Type done = types.get(type);

      if (done == null) {
         Class match = scanner.importType(type);
         
         if (match == null) {
            return null;
         }
         return loadType(match);
      }
      return done;
   }

   public synchronized Type loadType(String module, String name) {
      String alias = builder.createName(module, name);
      Type done = types.get(alias);

      if (done == null) {
         Class match = scanner.importType(alias);
         
         if (match == null) {
            return null;
         }
         return loadType(match);
      }
      return done;
   }

   public synchronized Type loadType(String module, String name, int size) {
      String alias = builder.createName(module, name, size);
      Type done = types.get(alias);
      
      if (done == null) {
         String type = builder.createName(module, name);
         Class match = scanner.importType(type, size);
         
         if (match == null) {
            if(size > 0) {
               return createType(module, name, size);
            }
            return defineType(module, name);
         }
         return loadType(match);
      }
      return done;
   }   
   
   public synchronized Type defineType(String module, String name) {
      String alias = builder.createName(module, name);
      Type done = types.get(alias);

      if (done == null) {
         Class match = scanner.importType(alias);
         
         if (match == null) {
            Type type = createType(module, name);
            
            types.put(type, type);
            types.put(alias, type);
            
            return type;
         }
         return loadType(match);
      }
      return done;
   }
   
   public synchronized Type loadType(Class source) {
      Type done = types.get(source);
      
      if (done == null) {
         String alias = scanner.importName(source);
         String absolute = source.getName();
         Type type = createType(source);

         types.put(source, type);
         types.put(alias, type);
         types.put(absolute, type);
         
         return type;
      }
      return done;
   }

   private synchronized Type createType(String module, String name) {
      String alias = builder.createName(module, name);
      String prefix = builder.createOuterName(module, name); 
      Module parent = registry.addModule(module);
      Type type = types.get(alias);
      
      if(type == null) {
         Type outer = types.get(prefix);
         int order = counter.getAndIncrement();
         
         if(order > limit) {
            throw new InternalStateException("Type limit of " + limit + " exceeded");
         }
         return new ScopeType(parent, outer, name, order);
      }
      return type;
   }
   
   private synchronized Type createType(String module, String name, int size) {
      String alias = builder.createName(module, name, size);
      Module parent = registry.addModule(module);
      Type type = types.get(alias);
      
      if(type == null) {
         Type entry = loadType(module, name, size -1);
         
         if(entry == null) {
            throw new InternalStateException("Type entry for '" +alias+ "' not found");
         }
         String array = builder.createName(null, name, size);
         int order = counter.getAndIncrement();
         
         if(order > limit) {
            throw new InternalStateException("Type limit of " + limit + " exceeded");
         }
         return new ScopeArrayType(parent, array, entry, size, order); // name is wrong here ScopeArrayType?
      }
      return type;
   }
   
   private synchronized Type createType(Class source) {
      String alias = scanner.importName(source);
      String name = source.getSimpleName();
      Type type = types.get(alias);
      
      if(type == null) {
         int order = counter.getAndIncrement();
         
         if(order > limit) {
            throw new InternalStateException("Type limit of " + limit + " exceeded");
         }
         return new ClassType(indexer, source, name, order);
      }
      return type;
   }
}
