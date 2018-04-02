package org.snapscript.core;

import org.snapscript.core.module.FilePathConverter;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.ModuleRegistry;
import org.snapscript.core.module.Path;
import org.snapscript.core.module.PathConverter;
import org.snapscript.core.scope.Model;
import org.snapscript.core.scope.ModelScope;
import org.snapscript.core.scope.Scope;

public class ScopeMerger {

   private final PathConverter converter;
   private final Context context;
   
   public ScopeMerger(Context context) {
      this.converter = new FilePathConverter();
      this.context = context;
   }
   
   public Scope merge(Model model, String name) {
      Path path = converter.createPath(name);
      
      if(path == null) {
         throw new InternalStateException("Module '" +name +"' does not have a path"); 
      }
      return merge(model, name, path);
   }
   
   public Scope merge(Model model, String name, Path path) {
      ModuleRegistry registry = context.getRegistry();
      Module module = registry.addModule(name, path);
      
      if(module == null) {
         throw new InternalStateException("Module '" +name +"' not found");
      }
      return new ModelScope(model, module);
   }
}