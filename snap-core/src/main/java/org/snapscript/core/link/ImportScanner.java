package org.snapscript.core.link;

import static org.snapscript.core.Reserved.IMPORT_JAVA;
import static org.snapscript.core.Reserved.IMPORT_JAVAX;
import static org.snapscript.core.Reserved.IMPORT_JAVA_IO;
import static org.snapscript.core.Reserved.IMPORT_JAVA_LANG;
import static org.snapscript.core.Reserved.IMPORT_JAVA_MATH;
import static org.snapscript.core.Reserved.IMPORT_JAVA_NET;
import static org.snapscript.core.Reserved.IMPORT_JAVA_UTIL;
import static org.snapscript.core.Reserved.IMPORT_SNAPSCRIPT;

import java.lang.reflect.Array;
import java.lang.Package;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.snapscript.core.TypeNameBuilder;
import org.snapscript.core.NameBuilder;

public class ImportScanner {
   
   private static final String[] DEFAULTS = {
      IMPORT_JAVA, 
      IMPORT_JAVAX,
      IMPORT_JAVA_LANG, 
      IMPORT_JAVA_UTIL, 
      IMPORT_JAVA_IO,
      IMPORT_JAVA_NET,     
      IMPORT_JAVA_MATH,
      IMPORT_SNAPSCRIPT
   };
   
   private final Map<String, Package> packages;
   private final Map<String, Class> types;
   private final Map<Object, String> names;
   private final ImportLoader loader;
   private final NameBuilder builder;
   private final Set<String> failures;
   private final String[] prefixes;
   
   public ImportScanner() {
      this(DEFAULTS);
   }
   
   public ImportScanner(String... prefixes) {
      this.packages = new ConcurrentHashMap<String, Package>();
      this.names = new ConcurrentHashMap<Object, String>();
      this.types = new ConcurrentHashMap<String, Class>();
      this.failures = new CopyOnWriteArraySet<String>();
      this.builder = new TypeNameBuilder();
      this.loader = new ImportLoader();
      this.prefixes = prefixes;
   }
   
   public Package importPackage(String name) {
      if(!failures.contains(name)) {
         Package result = packages.get(name);
         
         if(result == null) {
            result = loadPackage(name);
         }
         if(result == null) {
            for(String prefix : prefixes) {
               result = loadPackage(prefix + name);
               
               if(result != null) {
                  packages.put(name, result);
                  return result;
               }
            }   
            failures.add(name); // not found!!
         }
         return result;
      }
      return null;
   }

   public Class importType(String name) {
      if(!failures.contains(name)) {
         Class type = types.get(name);
         
         if(type == null) {
            type = loadType(name);
         }
         if(type == null) {
            for(String prefix : prefixes) {
               type = loadType(prefix + name);
               
               if(type != null) {
                  types.put(name, type);
                  return type;
               }
            }   
            failures.add(name); // not found!!
         }
         return type;
      }
      return null;
   }
   
   public Class importType(String name, int size) {
      Class type = importType(name);
      
      if(type != null && size < 4) {
         Object array = null;
         
         if(size > 0) {
            if(size == 1) {
               array = Array.newInstance(type, 0);
            } else if(size == 2){
               array = Array.newInstance(type, 0, 0);
            } else if(size == 3){
               array = Array.newInstance(type, 0, 0, 0);
            } 
            return array.getClass();
         }
         return type;
      }
      return null;
   }
   
   public String importName(Class type) {
      String result = names.get(type);
      
      if(result == null) {
         String absolute = builder.createFullName(type);
         
         for(String prefix : prefixes) {
            if(absolute.startsWith(prefix)) {
               int length = prefix.length();
               String name = absolute.substring(length);
               
               types.put(absolute, type);
               types.put(name, type);
               names.put(type, name);
               
               return name;
            }
            types.put(absolute, type);
            names.put(type, absolute);
         }   
         return absolute;
      }
      return result;
   }
   
   public String importName(Package module) {
      String result = names.get(module);
      
      if(result == null) {
         String absolute = module.getName();
         
         for(String prefix : prefixes) {
            if(absolute.startsWith(prefix)) {
               int length = prefix.length();
               String name = absolute.substring(length);
               
               packages.put(absolute, module);
               packages.put(name, module);
               names.put(module, name);
               
               return name;
            }
            packages.put(absolute, module);
            names.put(module, absolute);
         }   
         return absolute;
      }
      return result;
   }
   
   private Class loadType(String name) {
      try {
         Class result = loader.loadClass(name);

         if(result != null) {
            types.put(name, result);
         }
         return result;
      }catch(Exception e){
         return null;
      }
   }
   
   private Package loadPackage(String name) {
      try {
         Package result = loader.loadPackage(name);
         
         if(result != null) {
            packages.put(name, result);
         }
         return result;
      }catch(Exception e){
         return null;
      }
   }
}
