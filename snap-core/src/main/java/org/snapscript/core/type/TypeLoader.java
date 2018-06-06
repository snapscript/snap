package org.snapscript.core.type;

import org.snapscript.core.ResourceManager;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.link.ImportScanner;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageLinker;
import org.snapscript.core.link.PackageLoader;
import org.snapscript.core.link.PackageManager;
import org.snapscript.core.module.ModuleRegistry;
import org.snapscript.core.platform.PlatformProvider;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.extend.ClassExtender;
import org.snapscript.core.type.index.TypeIndexer;

public class TypeLoader {
   
   private final ImportScanner scanner;
   private final TypeExtractor extractor;
   private final ClassExtender extender;
   private final PlatformProvider provider;
   private final PackageManager manager;
   private final PackageLoader loader;
   private final TypeIndexer indexer;
   private final TypeCache cache;
   
   public TypeLoader(PackageLinker linker, ModuleRegistry registry, ResourceManager manager, ProxyWrapper wrapper, ThreadStack stack){
      this.extractor = new TypeExtractor(this);
      this.provider = new PlatformProvider(extractor, wrapper, stack);
      this.scanner = new ImportScanner(manager);
      this.extender = new ClassExtender(this);
      this.indexer = new TypeIndexer(registry, scanner, extender, provider);
      this.loader = new PackageLoader(linker, manager);
      this.manager = new PackageManager(loader, scanner);
      this.cache = new TypeCache(indexer);
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
   
   public Type defineType(String module, String name, int modifiers) {
      return indexer.defineType(module, name, modifiers);
   }
   
   public Type loadType(String module, String name) {
      return cache.fetch(module, name);
   }
   
   public Type loadArrayType(String module, String name, int size) {
      return indexer.loadArrayType(module, name, size); // no cache
   }
   
   public Type loadType(String type) {
      return cache.fetch(type);
   }
   
   public Type loadType(Class type) {
      return cache.fetch(type);
   } 
}