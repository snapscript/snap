package org.snapscript.compile.assemble;

import static org.snapscript.tree.Instruction.SCRIPT_PACKAGE;

import org.snapscript.common.Cache;
import org.snapscript.common.LeastRecentlyUsedCache;
import org.snapscript.core.Context;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageLinker;

public class ProgramLinker implements PackageLinker {
   
   private final Cache<String, Package> cache;
   private final PackageBuilder builder;  
   
   public ProgramLinker(Context context) {
      this.cache = new LeastRecentlyUsedCache<String, Package>();
      this.builder = new PackageBuilder(context);
   }
   
   @Override
   public Package link(String resource, String source) throws Exception {
      return link(resource, source, SCRIPT_PACKAGE.name);
   }
   
   @Override
   public Package link(String resource, String source, String grammar) throws Exception {
      Package linked = cache.fetch(resource);
      
      if(linked == null) {
         linked = builder.create(resource, source, grammar); 
         cache.cache(resource, linked);
      }
      return linked; 
   } 
}
