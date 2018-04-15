package org.snapscript.tree.define;

import org.snapscript.core.Context;
import org.snapscript.core.link.ImportManager;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.ModuleRegistry;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.NameBuilder;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.annotation.AnnotationList;

public class ModuleBuilder {

   private final AnnotationList annotations;
   private final NameReference reference;
   private final NameBuilder builder;
   
   public ModuleBuilder(AnnotationList annotations, ModuleName module) {
      this.reference = new NameReference(module);
      this.builder = new NameBuilder();
      this.annotations = annotations;
   }

   public Module create(Scope scope) throws Exception {
      String name = reference.getName(scope);
      Module parent = scope.getModule();
      Module module = create(parent, name);
      ImportManager manager = module.getManager();
      String include = parent.getName();
      
      annotations.apply(scope, module);
      manager.addImport(include); // make outer classes accessible
      
      return module;
   }
   
   protected Module create(Module parent, String name) throws Exception {
      Path path = parent.getPath();
      String prefix = parent.getName();
      String type = builder.createFullName(prefix, name);
      Context context = parent.getContext();
      ImportManager manager = parent.getManager();
      ModuleRegistry registry = context.getRegistry();
      Module module = registry.addModule(type, path); // create module
      
      manager.addImports(module); // add parent imports
      manager.addImport(type, name); // make module accessible by name
      
      return module;
   }
}