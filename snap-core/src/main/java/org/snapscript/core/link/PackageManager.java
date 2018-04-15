package org.snapscript.core.link;

import static org.snapscript.core.link.ImportType.EXPLICIT;
import static org.snapscript.core.link.ImportType.IMPLICIT;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.type.NameBuilder;
  
public class PackageManager {
   
   private final ImportScanner scanner;
   private final PackageLoader loader;
   private final NameBuilder builder;
   
   public PackageManager(PackageLoader loader, ImportScanner scanner) {
      this.builder = new NameBuilder();;
      this.scanner = scanner;
      this.loader = loader;
   }
   
   public Package importPackage(String module) {
      Object result = scanner.importPackage(module);
      
      if(result == null) {
         try {
            return loader.load(IMPLICIT, module); // import some.package.*
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
         String outer  = builder.createTopName(module, name); 
         
         try {
            return loader.load(EXPLICIT, outer, module); // import some.package.Blah
         } catch(Exception e){
            throw new InternalStateException("Problem importing '" + module + "." + name + "'", e);
         }
      }
      return new NoPackage();
   }
   
   public Package importType(String type) {           
      Object result = scanner.importType(type);
      
      if(result == null) {
         String outer  = builder.createTopName(type); 
         
         try {
            return loader.load(EXPLICIT, outer); 
         } catch(Exception e){
            throw new InternalStateException("Problem importing '" + type + "'", e);
         }
      }
      return new NoPackage();
   }
}