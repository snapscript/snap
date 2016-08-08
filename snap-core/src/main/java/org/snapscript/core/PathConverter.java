package org.snapscript.core;

import static org.snapscript.core.Reserved.SCRIPT_EXTENSION;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PathConverter {

   private final Map<String, String> modules;
   private final Map<String, String> paths;
   private final String suffix;
   
   public PathConverter() {
      this(SCRIPT_EXTENSION);
   }
   
   public PathConverter(String suffix) {
      this.modules = new ConcurrentHashMap<String, String>();
      this.paths = new ConcurrentHashMap<String, String>();
      this.suffix = suffix;
   }
   
   public String createPath(String resource) {
      String path = paths.get(resource);
      
      if(path == null) {
         path = convertModule(resource);
         paths.put(resource, path);
         paths.put(path, path);
      }
      return path;
   }

   public String createModule(String resource) {
      String module = modules.get(resource);
      
      if(module == null) {
         module = convertPath(resource);
         modules.put(resource, module);
         modules.put(module, module);
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
