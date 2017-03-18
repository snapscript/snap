
package org.snapscript.core.link;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Scope;

public class PackageList implements Package {
   
   private final List<Package> modules;
   
   public PackageList(List<Package> modules) {
      this.modules = modules;
   }

   @Override
   public PackageDefinition define(Scope scope) throws Exception {
      List<PackageDefinition> definitions = new ArrayList<PackageDefinition>();
      
      for(Package module : modules){
         PackageDefinition definition = module.define(scope);
         
         if(definition != null) {
            definitions.add(definition);
         }
      }
      return new PackageDefinitionList(definitions);
   }

}
