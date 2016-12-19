package org.snapscript.core;

public class ScopeMerger {

   private final PathConverter converter;
   private final Context context;
   
   public ScopeMerger(Context context) {
      this.converter = new PathConverter();
      this.context = context;
   }
   
   public Scope merge(Model model, String name) {
      String path = converter.createPath(name);
      
      if(path == null) {
         throw new InternalStateException("Module '" +name +"' does not have a path"); 
      }
      return merge(model, name, path);
   }
   
   public Scope merge(Model model, String name, String path) {
      ModuleRegistry registry = context.getRegistry();
      Module module = registry.addModule(name, path);
      
      if(module == null) {
         throw new InternalStateException("Module '" +name +"' not found");
      }
      return new ModelScope(model, module);
   }
}
