package org.snapscript.compile;

import static org.snapscript.tree.Instruction.SCRIPT;

import org.snapscript.common.Cache;
import org.snapscript.common.LeastRecentlyUsedCache;
import org.snapscript.compile.assemble.Program;
import org.snapscript.core.Context;
import org.snapscript.core.PathConverter;
import org.snapscript.core.ResourceManager;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageLinker;

public class ResourceCompiler implements Compiler {

   private final Cache<String, Executable> cache;
   private final PathConverter converter;
   private final Context context;   
   
   public ResourceCompiler(Context context) {
      this.cache = new LeastRecentlyUsedCache<String, Executable>();
      this.converter = new PathConverter();
      this.context = context;
   } 
   
   @Override
   public Executable compile(String resource) throws Exception {
      return compile(resource, false);
   }
   
   @Override
   public Executable compile(String resource, boolean verbose) throws Exception {
      if(resource == null) {
         throw new NullPointerException("No resource provided");
      }
      Executable executable = cache.fetch(resource);
      
      if(executable == null) {
         ResourceManager manager = context.getManager();
         String module = converter.createModule(resource);
         String source = manager.getString(resource);
         PackageLinker linker = context.getLinker();
         Package library = linker.link(module, source, SCRIPT.name);
  
         return new Program(context, library, module, resource);
      }
      return executable;
   } 
}
