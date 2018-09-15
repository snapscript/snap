package org.snapscript.compile.assemble;

import static org.snapscript.core.Reserved.GRAMMAR_PACKAGE;

import java.util.concurrent.Executor;

import org.snapscript.common.Cache;
import org.snapscript.common.LeastRecentlyUsedCache;
import org.snapscript.core.Context;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageLinker;
import org.snapscript.core.module.Path;

public class ApplicationLinker implements PackageLinker {
   
   private final Cache<Path, Package> cache;
   private final PackageBuilder builder;  
   
   public ApplicationLinker(Context context, Executor executor) {
      this.cache = new LeastRecentlyUsedCache<Path, Package>();
      this.builder = new PackageBuilder(context, executor);
   }
   
   @Override
   public Package link(Path path, String source) throws Exception {
      return link(path, source, GRAMMAR_PACKAGE);
   }
   
   @Override
   public Package link(Path path, String source, String grammar) throws Exception {
      Package linked = cache.fetch(path);
      
      if(linked == null) {
         linked = builder.create(path, source, grammar); 
         cache.cache(path, linked);
      }
      return linked; 
   } 
}