package org.snapscript.core;

import static org.snapscript.core.Reserved.SCRIPT_EXTENSION;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;

public class FilePathConverter implements PathConverter {

   private final Cache<String, String> modules;
   private final Cache<String, Path> paths;
   private final String suffix;
   
   public FilePathConverter() {
      this(SCRIPT_EXTENSION);
   }
   
   public FilePathConverter(String suffix) {
      this.modules = new CopyOnWriteCache<String, String>();
      this.paths = new CopyOnWriteCache<String, Path>();
      this.suffix = suffix;
   }
   
   public Path createPath(String resource) {
      Path path = paths.fetch(resource);
      
      if(path == null) {
         Path match = convertModule(resource);
         String value = match.getPath();
         
         paths.cache(resource, path);
         paths.cache(value, path);
         
         return match;
      }
      return path;
   }

   public String createModule(String resource) {
      String module = modules.fetch(resource);
      
      if(module == null) {
         String match = convertPath(resource);
         
         modules.cache(resource, match);
         modules.cache(match, match);
         
         return match;
      }
      return module;
   }
   
   private Path convertModule(String resource) {
      int index = resource.indexOf(suffix);
      
      if(index == -1) {
         int slash = resource.indexOf('.');
      
         if(slash != -1) {
            resource = resource.replace('.', '/');
         }
         return new Path("/" + resource + suffix);
      }
      return new Path(resource);
   }
   
   private String convertPath(String path) {
      String qualifier = convertResource(path);
      int index = qualifier.lastIndexOf('.');
      
      if(index != -1) {
         char value = qualifier.charAt(index+1);
         
         if(Character.isUpperCase(value)) {
            return qualifier.substring(0,index);
         }
      }
      return qualifier;
      
   }
   
   private String convertResource(String path) {
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
