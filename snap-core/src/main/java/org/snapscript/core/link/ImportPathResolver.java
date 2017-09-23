package org.snapscript.core.link;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ImportPathResolver {
  
   private final ImportPathSource source;
   
   public ImportPathResolver(String file) {
      this.source = new ImportPathSource(file);
   }
   
   public String resolveName(String resource) {
      ImportPath path = source.getPath();
      Map<String, String> aliases = path.getAliases();
      Set<String> names = aliases.keySet();
      
      for(String name : names) {
         String prefix = aliases.get(name);
         
         if(resource.startsWith(prefix)) {
            return resource.replace(prefix, name);
         }
      }
      return resource;
   }
   
   public List<String> resolvePath(String resource) {
      int index = resource.indexOf('.');
      
      if(index != -1) {
         return resolveAliasPath(resource, index);
      }
      return resolveTypePath(resource);
   }
   
   private List<String> resolveAliasPath(String resource, int index) {
      ImportPath path = source.getPath();
      Map<String, String> aliases = path.getAliases();
      String token = resource.substring(0, index);
      String prefix = aliases.get(token); // lang -> java.lang. 
      
      if(prefix != null) {
         List<String> list = new ArrayList<String>();
         StringBuilder builder = new StringBuilder();
         
         String remainder = resource.substring(index);
         
         builder.append(prefix);
         builder.append(remainder);
         
         String absolute = builder.toString();
         
         list.add(absolute);
         list.add(resource);
         
         return list; // lang.String -> [java.lang.String, lang.String]
      }
      return Collections.singletonList(resource); // com.w3c.Document -> [com.w3c.Document]
   }
   
   private List<String> resolveTypePath(String resource) {
      ImportPath path = source.getPath();
      Map<String, String> types = path.getTypes();
      String module = types.get(resource); // String -> java.lang.
      
      if(module != null) {
         List<String> list = new ArrayList<String>();
         StringBuilder builder = new StringBuilder();
         
         builder.append(module);
         builder.append(".");
         builder.append(resource);
         
         String absolute = builder.toString();
         
         list.add(absolute);
         
         return list; // String -> java.lang.String
      }
      return resolveDefaultPath(resource);
   }
   
   private List<String> resolveDefaultPath(String resource) {
      ImportPath path = source.getPath();
      Set<String> defaults = path.getDefaults();
      
      if(resource != null) {
         List<String> list = new ArrayList<String>();
         StringBuilder builder = new StringBuilder();
         
         for(String prefix : defaults){
            builder.append(prefix);
            builder.append(".");
            builder.append(resource);
            
            String entry = builder.toString();
            
            list.add(entry);
            builder.setLength(0);
         }
         return list; // String -> [java.lang.String, java.net.String, java.io.String]
      }
      return Collections.emptyList();
   }
}