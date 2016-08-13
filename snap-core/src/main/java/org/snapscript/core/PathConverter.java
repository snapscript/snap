package org.snapscript.core;

import static org.snapscript.core.Reserved.SCRIPT_EXTENSION;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;

public class PathConverter {

   private final Cache<String, String> modules;
   private final Cache<String, String> paths;
   private final String suffix;
   
   public PathConverter() {
      this(SCRIPT_EXTENSION);
   }
   
   public PathConverter(String suffix) {
      this.modules = new CopyOnWriteCache<String, String>();
      this.paths = new CopyOnWriteCache<String, String>();
      this.suffix = suffix;
   }
   
   public String createPath(String resource) {
      String path = paths.fetch(resource);
      
      if(path == null) {
         path = convertModule(resource);
         paths.cache(resource, path);
         paths.cache(path, path);
      }
      return path;
   }

   public String createModule(String resource) {
      String module = modules.fetch(resource);
      
      if(module == null) {
         module = convertPath(resource);
         modules.cache(resource, module);
         modules.cache(module, module);
      }
      return module;
   }
   
   private String convertModule(String resource) {
      int index = resource.indexOf(suffix);
      
      if(index == -1) {
         int slash = resource.indexOf('.');
      
         if(slash != -1) {
            resource = resource.replace('.', '/');
         }
         return "/" + resource + suffix;
      }
      return resource;
   }
   
   private String convertPath(String path) {
      int index = path.indexOf(suffix);

      if(index != -1) {
         path = path.substring(0, index);
      }
      if(path.startsWith("/")) {
         path = path.substring(1);
      }
      if(path.startsWith("\\")) {
         path = path.substring(1);
      }
      if(path.contains("\\")) {
         path = path.replace("\\", ".");
      }
      return path.replace('/',  '.');
   }
}
