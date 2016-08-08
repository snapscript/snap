package org.snapscript.core.link;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.PathConverter;
import org.snapscript.core.ResourceManager;

public class PackageLoader {
   
   private final PathConverter converter;
   private final ResourceManager manager;
   private final PackageLinker linker;
   private final Set libraries;

   public PackageLoader(PackageLinker linker, ResourceManager manager){
      this.libraries = new CopyOnWriteArraySet();
      this.converter = new PathConverter();
      this.manager = manager;
      this.linker = linker;
   }

   public Package load(String qualifier) throws Exception {
      if(libraries.add(qualifier)) { // load only once!
         String path = converter.createPath(qualifier);
         String source = manager.getString(path); // load source code
         
         try {
            return linker.link(qualifier, source);
         } catch(Exception e) {
            throw new InternalStateException("Could not load library '" + path + "'", e);
         }
      }
      return new NoPackage();
   }
}
