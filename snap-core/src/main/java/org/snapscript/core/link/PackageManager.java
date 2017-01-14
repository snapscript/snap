package org.snapscript.core.link;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.NameBuilder;
import org.snapscript.core.TypeNameBuilder;
  
public class PackageManager {
   
   private final ImportScanner scanner;
   private final PackageLoader loader;
   private final NameBuilder builder;
   
   public PackageManager(PackageLoader loader, ImportScanner scanner) {
      this.builder = new TypeNameBuilder();;
      this.scanner = scanner;
      this.loader = loader;
   }
   
   public Package importPackage(String module) {
      Object result = scanner.importPackage(module);
      
      if(result == null) {
         try {
            return loader.load(module);
         } catch(Exception e){
            throw new InternalStateException("Problem importing '" + module + "'", e);
         }
      }
      return new NoPackage();
   }
   
   public Package importType(String module, String name) {
      String type  = builder.createFullName(module, name);
      Object result = scanner.importType(type);
      
      if(result == null) {
         try {
            return loader.load(module, type);
         } catch(Exception e){
            throw new InternalStateException("Problem importing '" + module + "." + name + "'", e);
         }
      }
      return new NoPackage();
   }
}
