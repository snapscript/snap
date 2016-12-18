package org.snapscript.core;

import org.snapscript.core.extend.ClassExtender;
import org.snapscript.core.index.TypeIndexer;
import org.snapscript.core.link.ImportScanner;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageLinker;
import org.snapscript.core.link.PackageLoader;
import org.snapscript.core.link.PackageManager;
import org.snapscript.core.thread.ThreadStack;

public class TypeLoader {
   
   private final PackageManager manager;
   private final PackageLoader loader;
   private final ImportScanner scanner;
   private final TypeIndexer indexer;
   private final ClassExtender extender;
   
   public TypeLoader(PackageLinker linker, ModuleRegistry registry, ResourceManager manager, ThreadStack stack){
      this.scanner = new ImportScanner();
      this.extender = new ClassExtender(this, stack);
      this.indexer = new TypeIndexer(registry, scanner, extender, stack);
      this.loader = new PackageLoader(linker, manager);
      this.manager = new PackageManager(loader, scanner);
   }
   
   public Package importPackage(String module)  {
      return manager.importPackage(module);
   }   
   
   public Package importType(String module, String name) {
      return manager.importType(module, name); 
   }
   
   public Type defineType(String module, String name) {
      return indexer.defineType(module, name);
   }
   
   public Type resolveType(String module, String name) {
      return indexer.loadType(module, name);
   }
   
   public Type resolveType(String module, String name, int size) {
      return indexer.loadType(module, name, size);
   }
   
   public Type resolveType(String type) {
      return indexer.loadType(type);
   }
   
   public Type loadType(Class type) {
      return indexer.loadType(type);
   } 
}
