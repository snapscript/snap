package org.snapscript.core.link;

import java.util.List;

public class PackageMerger {

   private final Package empty;
   
   public PackageMerger() {
      this.empty = new NoPackage();
   }
   
   public Package merge(List<Package> modules) {
      if(!modules.isEmpty()) {
         int size = modules.size();
         
         if(size > 1) {
            return new PackageList(modules);
         }
         return modules.get(0);
      }
      return empty;
   }
}
