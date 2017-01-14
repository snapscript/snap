package org.snapscript.core.link;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.snapscript.core.FilePathConverter;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Path;
import org.snapscript.core.PathConverter;
import org.snapscript.core.ResourceManager;

public class PackageLoader {
   
   private final PathConverter converter;
   private final ResourceManager manager;
   private final PackageLinker linker;
   private final PackageMerger merger;
   private final Set libraries;

   public PackageLoader(PackageLinker linker, ResourceManager manager){
      this.libraries = new CopyOnWriteArraySet();
      this.converter = new FilePathConverter();
      this.merger = new PackageMerger();
      this.manager = manager;
      this.linker = linker;
   }

   public Package load(String... list) throws Exception {
      List<Package> modules = new ArrayList<Package>(list.length);
      Set<String> complete = new HashSet<String>(list.length);
      StringBuilder message = new StringBuilder();
      
      for(int i = 0; i < list.length; i++) {
         String resource = list[i];
         
         if(libraries.add(resource) && complete.add(resource)) { // load only once!
            Path path = converter.createPath(resource);
            String location = path.getPath();
            String source = manager.getString(location); // load source code
            
            try {
               if(source != null) {
                  Package module = linker.link(path, source);
                  
                  if(module != null) {
                     modules.add(module);
                  }
               } else {
                  int size = message.length();
                  
                  if(size > 0) {
                     message.append(" or ");
                  }
                  message.append("'");
                  message.append(path);
                  message.append("'");
               }
            } catch(Exception e) {
               throw new InternalStateException("Could not load library '" + resource + "'", e);
            }
         }
      }
      int check = complete.size(); // how many did we check
      int found = modules.size(); // how many did we find
      
      if(found == 0 && check == list.length) {
         throw new InternalStateException("Could not load library " + message);
      }
      return merger.merge(modules);
   }
}
