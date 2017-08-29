package org.snapscript.core;


import org.snapscript.core.bridge.BridgeProvider;
import org.snapscript.core.bridge.PlatformBridgeProvider;
import org.snapscript.core.extend.ClassExtender;
import org.snapscript.core.index.TypeIndexer;
import org.snapscript.core.link.ImportScanner;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageLinker;
import org.snapscript.core.link.PackageLoader;
import org.snapscript.core.link.PackageManager;
import org.snapscript.core.stack.ThreadStack;

public class TypeLoader {
   
   private final TypeExtractor extractor;
   private final BridgeProvider provider;
   private final PackageManager manager;
   private final PackageLoader loader;
   private final ImportScanner scanner;
   private final TypeIndexer indexer;
   private final ClassExtender extender;
   
   public TypeLoader(PackageLinker linker, ModuleRegistry registry, ResourceManager manager, ThreadStack stack){
      this.extractor = new TypeExtractor(this);
      this.provider = new PlatformBridgeProvider(extractor, stack);
      this.scanner = new ImportScanner(manager);
      this.extender = new ClassExtender(this);
      this.indexer = new TypeIndexer(registry, scanner, extender, provider);
      this.loader = new PackageLoader(linker, manager);
      this.manager = new PackageManager(loader, scanner);
   }
   
   public Package importPackage(String module)  {
      return manager.importPackage(module);
   }   
   
   public Package importType(String type) {
      return manager.importType(type);  // import a runtime
   }
   
   public Package importType(String module, String name) {
      return manager.importType(module, name); 
   }
   
      return indexer.defineType(module, name, category);
   }
   
   public Type resolveType(String module, String name) {
      return indexer.loadType(module, name);
   }
   
   public Type resolveArrayType(String module, String name, int size) {
      return indexer.loadArrayType(module, name, size); // array type
   }
   
   public Type resolveType(String type) {
      return indexer.loadType(type);
   }
   
   public Type loadType(Class type) {
      return indexer.loadType(type);
   } 
}